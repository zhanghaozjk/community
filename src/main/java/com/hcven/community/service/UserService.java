package com.hcven.community.service;

import com.hcven.community.data.User;
import com.hcven.community.dto.UserSecureData;

/**
 * @author zhanghao
 * @since 2019-03-16
 */
public interface UserService {

    /**
     * 验证邮箱注册额
     * @param email
     * @return
     */
    Boolean checkEmailExist(String email);

    /**
     * 用户登录
     * @param user
     * @return
     */
    Boolean login(User user);

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
}
