package com.hcven.community.service.impl;

import com.hcven.community.data.User;
import com.hcven.community.data.UserRole;
import com.hcven.community.dto.UserSecureData;
import com.hcven.community.mapper.UserMapper;
import com.hcven.community.mapper.UserRoleMapper;
import com.hcven.community.service.UserService;
import com.hcven.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanghao
 * @since 2019-03-16
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper) {this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 邮箱查重
     * @param email email
     * @return is exists
     */
    @Override
    public Boolean checkEmailExist(String email) {
        Boolean exist = Boolean.FALSE;
        if (userMapper.checkEmailExists(email) > 0) {
            exist = true;
        }
        return exist;
    }

    @Override
    public Boolean login(User user) {
        return true;
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

    private UserSecureData convertUser2UserSecureData(User user, UserRole role) {
        if (user == null) {
            return null;
        }
        UserSecureData data = new UserSecureData();
        data.setUsername(user.getUsername());
        data.setId(user.getId());
        data.setPassword(user.getPassword());
        data.setEmail(user.getEmail());
        if (role != null) {
            data.setRole(role.getRole());
            data.setPermission(role.getPermission());
        }
        return data;
    }
}
