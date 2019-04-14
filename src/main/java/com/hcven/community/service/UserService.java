package com.hcven.community.service;

import com.hcven.community.data.User;
import com.hcven.community.dto.UserSecureData;
import com.hcven.community.vo.RegistVO;

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
    RegistVO emailRegist(RegistVO registVO);

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
}
