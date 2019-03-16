package com.hcven.community.service.impl;

import com.hcven.community.mapper.UserMapper;
import com.hcven.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanghao
 * @since 2019-03-16
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {this.userMapper = userMapper;}

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
}
