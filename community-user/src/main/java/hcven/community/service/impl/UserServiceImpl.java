package com.hcven.community.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hcven.community.dao.UserFollowDAO;
import com.hcven.community.data.User;
import com.hcven.community.data.UserRole;
import com.hcven.community.data.UserTag;
import com.hcven.community.dto.UserSecureData;
import com.hcven.community.mapper.UserMapper;
import com.hcven.community.mapper.UserRoleMapper;
import com.hcven.community.mapper.UserTagMapper;
import com.hcven.community.service.UserService;
import com.hcven.community.vo.RegistVO;
import com.hcven.community.vo.UserInformationVO;
import com.hcven.community.web.mail.MailService;
import com.hcven.core.constant.MailConstant;
import com.hcven.system.entity.Mail;
import com.hcven.system.exception.ServerException;
import com.hcven.utils.ApplicationUtils;
import com.hcven.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-03-16
 */
@Service
@Component
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private final MailService mailService;

    private final UserFollowDAO userFollowDAO;

    private final UserTagMapper userTagMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper, MailService mailService, UserFollowDAO userFollowDAO, UserTagMapper userTagMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.mailService = mailService;
        this.userFollowDAO = userFollowDAO;
        this.userTagMapper = userTagMapper;
    }

    public static class UserStatus {
        public static final Integer USER_NEED_VERIFY_EMAIL = 0;
        public static final Integer USER_NORMAL = 1;
        public static final Integer USER_NOT_IN_USE = 2;
    }

    /**
     * 邮箱查重
     *
     * @param email email
     * @return is exists
     */
    @Override
    public Map<String, Object> checkEmailExist(String email) {
        Boolean exist = Boolean.FALSE;
        Map<String, Object> map = new HashMap<>(4);
        if (userMapper.checkEmailExists(email) > 0) {
            exist = Boolean.TRUE;
        }
        map.put("email", email);
        map.put("exist", exist);
        return map;
    }

    @Override
    public RegistVO emailRegisterSend(RegistVO registVO) {
        UserSecureData secure = getUser(registVO.getUsername());
        if (secure != null) {

            String verifyCode = secure.getVerifyCode();

            Mail mail = new Mail();
            String[] to = {registVO.getEmail()};
            mail.setSubject(MailConstant.TEMPLATE_SUBJECT);
            mail.setTemplateName(MailConstant.REGISTER_TEMPLATE);
            Map<String, String> map = new HashMap<>(16);
            map.put("identifyCode", verifyCode);
            map.put("to", registVO.getEmail());
            mail.setTemplateModel(map);
            mail.setTo(to);
            mailService.sendTemplateMail(mail);
        } else {
            throw new ServerException("用户邮箱不存在");
        }
        return registVO;
    }

    @Override
    public Map<String, Object> emailCodeVerify(RegistVO registVO) {
        Map<String, Object> map = new HashMap<>(4);
        UserSecureData secure = getUser(registVO.getUsername());
        map.put("email", registVO.getEmail());
        if (secure.getVerifyCode().equals(registVO.getVerifyCode())) {
            User user = new User();
            user.setId(secure.getId());
            user.setStatus(UserStatus.USER_NORMAL);
            userMapper.updateByPrimaryKeySelective(user);
            map.put("verify", true);
        } else {
            map.put("verify", false);
        }
        return map;
    }

    @Override
    public String userGetNicknameByUserId(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null && !StringUtils.isEmpty(user.getNickname())) {
            return user.getNickname();
        } else {
            return "匿名用户";
        }
    }

    @Override
    public Boolean userInformationUpdate(UserInformationVO informationVO, String username) {
        if (informationVO == null) {
            return false;
        }
        User user = userMapper.getUserByUsername(username);
        if (user != null) {
            if (!StringUtils.isEmpty(informationVO.getNickname())) {
                updateNickname(user, informationVO.getNickname());
            }
            UserTag userTag = userTagMapper.selectByUserId(user.getId());
            if (userTag == null) {
                userTag = insertUserTagIfNotExistsRecord(user.getId());
            }
            userTag.setCatWeight(informationVO.getCatWeight());
            userTag.setDogWeight(informationVO.getDogWeight());
            if (informationVO.getOtherWeight() != null && informationVO.getOtherWeight() != 0) {
                userTag.setOtherWeight(informationVO.getOtherWeight());
            } else {
                userTag.setOtherWeight(0);
            }
            userTagMapper.updateByPrimaryKeySelective(userTag);
            return true;
        }
        return false;
    }

    private void updateNickname(User user, String nickname) {
        if (user != null && !StringUtils.isEmpty(nickname) && !user.getNickname().equals(nickname)) {
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setNickname(nickname);
            userMapper.updateByPrimaryKeySelective(updateUser);
        }
    }

    private UserTag insertUserTagIfNotExistsRecord(Long userId) {
        // 冷启动更新
        UserTag userTag = new UserTag();
        userTag.setUserId(userId);
        userTag.setCatWeight(50);
        userTag.setDogWeight(50);
        userTag.setOtherWeight(0);
        userTagMapper.insert(userTag);
        return userTag;
    }

    @Override
    public UserInformationVO userInformationGet(String username) {
        UserInformationVO informationVO = null;
        if (username != null) {
            User user = userMapper.getUserByUsername(username);
            if (user != null) {
                informationVO = new UserInformationVO();
                informationVO.setNickname(user.getNickname());
                UserTag userTag = userTagMapper.selectByUserId(user.getId());
                if (userTag == null) {
                    userTag = insertUserTagIfNotExistsRecord(user.getId());
                }
                informationVO.setCatWeight(userTag.getCatWeight());
                informationVO.setDogWeight(userTag.getDogWeight());
                informationVO.setOtherWeight(userTag.getOtherWeight());
            }
        }
        return informationVO;
    }

    @Override
    public UserTag getUserTag(String username) {
        UserSecureData user = getUser(username);
        UserTag userTag = null;
        if (user != null) {
            userTag = userTagMapper.selectByUserId(user.getId());
            if (userTag == null) {
                // 冷启动
                userTag = insertUserTagIfNotExistsRecord(user.getId());
            }
        }
        return userTag;
    }

    @Override
    public Integer getFollowerCount(Long userId) {
        return userFollowDAO.getFollowerCount(userId);
    }

    @Override
    public Integer getFollowingCount(Long userId) {
        return userFollowDAO.getFollowingCount(userId);
    }

    @Override
    public UserSecureData getUser(String username) {
        User user = userMapper.getUserByUsername(username);
        UserRole role = null;
        if (null != user && user.getId() != null) {
            role = userRoleMapper.selectByPrimaryKey(user.getId());
        }
        return convertUser2UserSecureData(user, role);
    }

    @Override
    public String tokenUpdate(String token) {
        String username = JWTUtil.getUsername(token);
        UserSecureData user = getUser(username);
        if (user != null) {
            boolean truth = JWTUtil.verifyTokenExpired(token, user.getUsername(), user.getPassword());
            if (truth) {
                return JWTUtil.sign(user.getUsername(), user.getPassword());
            }
        }
        return null;
    }

    @Override
    public RegistVO emailRegister(RegistVO registVO) {
        User user = convertRegistVO2User(registVO);
        Map<String, Object> map = checkEmailExist(registVO.getEmail());
        if ((Boolean) map.get("exist")) {
            throw new ServerException("email exists");
        }
        int count;
        if (user != null) {
            user.setCreateTime(ApplicationUtils.getCurrentDateYMDHMS());
            user.setStatus(UserStatus.USER_NEED_VERIFY_EMAIL);
            // 随机一个username
            user.setNickname("user_" + ApplicationUtils.getNumStringRandom(4));
            try {
                user.setVerifyCode(ApplicationUtils.getNumStringRandom(6));
                count = userMapper.insert(user);
            } catch (Exception e) {
                throw new ServerException("添加用户错误");
            }
            if (count > 0) {
                registVO.setUsername(user.getEmail());
                return registVO;
            }
        }
        return null;
    }

    private User convertRegistVO2User(RegistVO registVO) {
        if (registVO == null) {
            return null;
        } else {
            User user = new User();
            user.setEmail(registVO.getEmail());
            user.setPassword(registVO.getPassword());
            user.setUsername(registVO.getEmail());
            return user;
        }
    }

    private UserSecureData convertUser2UserSecureData(User user, UserRole role) {
        if (user == null) {
            return null;
        }
        UserSecureData data = new UserSecureData();
        data.setUsername(user.getUsername());
        data.setId(user.getId());
        data.setPassword(user.getPassword());
        data.setEmail(user.getEmail());
        data.setVerifyCode(user.getVerifyCode());
        data.setStatus(user.getStatus());
        data.setNickname(user.getNickname());
        if (role != null) {
            data.setRole(role.getRole());
            data.setPermission(role.getPermission());
        }
        return data;
    }
}
