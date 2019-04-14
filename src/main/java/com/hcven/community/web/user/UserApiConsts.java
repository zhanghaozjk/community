package com.hcven.community.web.user;

/**
 * @author zhanghao
 * @since 2019-03-20
 */
public class UserApiConsts {
    public static class UserStatus {
        public static final Integer USER_NEED_VERIFY_EMAIL = 0;
        public static final Integer USER_NORMAL = 1;
        public static final Integer USER_NOT_IN_USE = 2;
    }

    public static final String COMMUNITY_EXPORT_API_EMAIL_CHECK = "/community/export/api/email/check";
    public static final String COMMUNITY_EXPORT_API_USER_LOGIN = "/community/export/api/user/login";
    public static final String COMMUNITY_EXPORT_API_USER_TOKEN_UPDATE = "/community/export/api/user/token/update";
    public static final String COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER = "/community/export/api/user/email/register";
    public static final String COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER_SEND_CODE = "/community/export/api/user/email/register/send/code";
    public static final String COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER_VERIFY = "/community/export/api/user/email/register/verify";
    //todo 注册接口 邮箱登陆接口
}
