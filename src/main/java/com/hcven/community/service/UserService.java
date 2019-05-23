package com.hcven.community.service;

import com.hcven.community.data.PostTag;
import com.hcven.community.data.UserTag;
import com.hcven.community.dto.UserSecureData;
import com.hcven.community.vo.MineUserVO;
import com.hcven.community.vo.RegistVO;
import com.hcven.community.vo.UserInformationVO;

import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-03-16
 */
public interface UserService {

    /**
     * 验证邮箱注册
     * @param email
     * @return
     */
    Map<String, Object> checkEmailExist(String email);

    /**
     * 通过username 获得user
     * @param username username
     * @return 改名字的user
     */
    UserSecureData getUser(String username);

    /**
     * 通过过期的token来更新token
     * @param token
     * @return
     */
    String tokenUpdate(String token);

    /**
     * 邮箱注册
     * @param registVO
     * @return
     */
    RegistVO emailRegister(RegistVO registVO);

    /**
     * 邮箱注册验证-发送code
     * @param registVO
     * @return
     */
    RegistVO emailRegisterSend(RegistVO registVO);

    /**
     * 邮箱验证code
     * @param registVO
     * @return
     */
    Map<String, Object> emailCodeVerify(RegistVO registVO);

    /**
     * 查看用户详情页的信息
     * @param username 用户名 为null则为查询自己
     * @return
     */
    MineUserVO getMineUserDetail(String username);

    /**
     * 通过id获取昵称
     * @param userId
     * @return
     */
    String userGetNicknameByUserId(Long userId);

    /**
     * 仅允许更新用户自己的信息
     * @param informationVO
     * @return
     */
    Boolean userInformationUpdate(UserInformationVO informationVO);

    /**
     * 获取用户的详情信息
     * @param username
     * @return
     */
    UserInformationVO userInformationGet(String username);

    /**
     * 获取用户tag
     * @param username
     * @return
     */
    UserTag getUserTag(String username);
}
