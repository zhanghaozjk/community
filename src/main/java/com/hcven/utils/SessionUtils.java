package com.hcven.utils;

import com.hcven.system.exception.UnauthorizedException;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhanghao
 * @since 2019-04-20
 */
public class SessionUtils {
    private static final Logger logger = LoggerFactory.getLogger(SessionUtils.class);

    public static String getUsername() throws UnauthorizedException {
        return JWTUtil.getUsername(SecurityUtils.getSubject().getPrincipal().toString());
    }
}
