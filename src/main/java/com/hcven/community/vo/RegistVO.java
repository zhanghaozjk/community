package com.hcven.community.vo;

import java.io.Serializable;

/**
 * @author zhanghao
 * @since 2019-04-13
 */
public class RegistVO implements Serializable {
    private String email;
    private String password;
    private String verifyCode;
    private String username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
