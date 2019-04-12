package com.hcven.community.web.user;

import com.hcven.community.data.common.CommonRes;
import com.hcven.community.dto.UserSecureData;
import com.hcven.community.service.UserService;
import com.hcven.community.vo.EmailVO;
import com.hcven.system.exception.UnauthorizedException;
import com.hcven.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-03-16
 */
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @PostMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_EMAIL_CHECK)
    public Boolean checkEmailExists(@RequestBody EmailVO email) {
        return userService.checkEmailExist(email.getEmail());
    }

    @PostMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_USER_LOGIN)
    public CommonRes login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        UserSecureData user = userService.getUser(username);
        if (user.getPassword().equals(password)) {
            Map<String, String> data = new HashMap<>();
            data.put("token", JWTUtil.sign(username, password));
            return new CommonRes(200, "Login success", data);
        } else {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_USER_TOKEN_UPDATE)
    public CommonRes tokenUpdate(@RequestParam("token") String token) {
        if (StringUtils.isEmpty(token)) {
            return new CommonRes(401, "auth error", null);
        }
        String newToken = userService.tokenUpdate(token);
        if (!StringUtils.isEmpty(newToken)) {
            Map<String, String> data = new HashMap<>();
            data.put("token", newToken);
            return new CommonRes(401, "need re-auth", data);
        } else {
            throw new UnauthorizedException();
        }
    }


}
