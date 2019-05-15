package com.hcven.community.web.user;

import com.hcven.community.data.Comment;
import com.hcven.community.data.common.CommonRes;
import com.hcven.community.dto.UserSecureData;
import com.hcven.community.service.PostService;
import com.hcven.community.service.UserService;
import com.hcven.community.vo.EmailVO;
import com.hcven.community.vo.RegistVO;
import com.hcven.community.vo.UserInformationVO;
import com.hcven.system.exception.ServerException;
import com.hcven.system.exception.UnauthorizedException;
import com.hcven.utils.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @PostMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_EMAIL_CHECK)
    public CommonRes checkEmailExists(@RequestBody EmailVO email) {
        return new CommonRes(HttpStatus.OK.value(), "check email",
                userService.checkEmailExist(email.getEmail()));
    }

    @PostMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_USER_LOGOUT)
    public CommonRes logout() {
        Subject subject = SecurityUtils.getSubject();
        //注销
        subject.logout();
        return CommonRes.message("logout");
    }

    @PostMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_USER_LOGIN)
    public CommonRes login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        UserSecureData user = userService.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            Map<String, String> data = new HashMap<>();
            if (UserApiConsts.UserStatus.USER_NORMAL.equals(user.getStatus())) {
                data.put("token", JWTUtil.sign(username, password));
                return new CommonRes(200, "Login success", data);
            } else if (UserApiConsts.UserStatus.USER_NEED_VERIFY_EMAIL.equals(user.getStatus())) {
                data.put("status", "0");
                data.put("username", user.getUsername());
                data.put("email", user.getUsername());
                return new CommonRes(200, "email need verify", data);
            } else {
                throw new UnauthorizedException();
            }
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

    @PostMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER)
    public CommonRes userRegister(@RequestBody RegistVO regist) {
        if (regist == null) {
            return new CommonRes(400, "Un-expect param", null);
        } else {
            try {
                RegistVO newUser = userService.emailRegister(regist);
                return new CommonRes(HttpStatus.OK.value(), regist.getEmail(), newUser);
            } catch (ServerException e) {
                return new CommonRes(HttpStatus.OK.value(), e.getMessage(), null);
            }
        }
    }

    @PostMapping(value = UserApiConsts.COMMUNITY_API_USER_MINE_DETAIL_GET)
    @RequiresAuthentication
    public CommonRes userDetails(@RequestParam(value = "username", required = false)String username) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("mineUserVO", userService.getMineUserDetail(username));
        return CommonRes.retOk(data);
    }

    @PostMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER_SEND_CODE)
    public CommonRes userEmailSendCode(@RequestBody RegistVO regist) {
        if (regist == null) {
            return new CommonRes(400, "Un-expect param", null);
        } else {
            RegistVO newUser = userService.emailRegisterSend(regist);
            if (newUser != null) {
                return new CommonRes(HttpStatus.OK.value(), regist.getEmail(), newUser);
            } else {
                throw new ServerException("用户名验证失败，请重试");
            }
        }
    }

    @PostMapping(value = UserApiConsts.COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER_VERIFY)
    public CommonRes userEmailVerify(@RequestBody RegistVO regist) {
        if (regist == null) {
            return new CommonRes(400, "Un-expect param", null);
        } else {
            Map ret = userService.emailCodeVerify(regist);
            return new CommonRes(200, null, ret);
        }
    }

    @PutMapping(value = UserApiConsts.COMMUNITY_API_USER_INFORMATION)
    @RequiresAuthentication
    public CommonRes userInformationUpdate(@RequestBody UserInformationVO userInformationVO) {
        if (userInformationVO == null) {
            return CommonRes.message("requestBody Error");
        } else {
            Map<String, Object> data = new HashMap<>(4);
            Boolean success = userService.userInformationUpdate(userInformationVO);
            data.put("success", success);
            return CommonRes.retOk(data);
        }
    }

    @GetMapping(value = UserApiConsts.COMMUNITY_API_USER_INFORMATION)
    @RequiresAuthentication
    public CommonRes userInformationGet(@RequestParam(value = "username", required = false) String username) {
        Map<String, Object> data = new HashMap<>(4);
        UserInformationVO informationVO = userService.userInformationGet(username);
        data.put("userInformationVO", informationVO);
        return CommonRes.retOk(data);
    }
}
