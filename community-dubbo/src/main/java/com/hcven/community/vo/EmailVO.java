package com.hcven.community.vo;

/**
 * @author zhanghao
 * @since 2019-03-16
 */
public class EmailVO {
    private String email;
    private String password;

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

    @Override
    public String toString() {
        return "{" + "\'email\':\'" + email + '\'' + "," + "\'password\':\'" + password + '\'' + '}';
    }
}
