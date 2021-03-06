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

    public static final String COMMUNITY_EXPORT_API_CHECK_SUCCESS = "/community/export/api/check/success";
    public static final String COMMUNITY_EXPORT_API_EMAIL_CHECK = "/community/export/api/email/check";
    public static final String COMMUNITY_EXPORT_API_USER_LOGIN = "/community/export/api/user/login";
    public static final String COMMUNITY_EXPORT_API_USER_TOKEN_UPDATE = "/community/export/api/user/token/update";
    public static final String COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER = "/community/export/api/user/email/register";
    public static final String COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER_SEND_CODE = "/community/export/api/user/email/register/send/code";
    public static final String COMMUNITY_EXPORT_API_USER_EMAIL_REGISTER_VERIFY = "/community/export/api/user/email/register/verify";
    public static final String COMMUNITY_EXPORT_API_USER_LOGOUT = "/community/export/api/user/logout";
    public static final String COMMUNITY_API_USER_INFORMATION = "/community/api/user/mine/information";
}
