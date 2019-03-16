package com.hcven.community.vo;

import java.util.List;

/**
 * @author zhanghao
 * @since 2019-03-12
 */
public class UserVO {

    /**
     * 用户ID 用户不可见
     */
    private Long id;
    /**
     * 电话号码
     */
    private Long phoneNumber;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 关注者
     */
    private List<Long> followers;
    /**
     * 关注列表
     */
    private List<Long> following;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Long> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Long> followers) {
        this.followers = followers;
    }

    public List<Long> getFollowing() {
        return following;
    }

    public void setFollowing(List<Long> following) {
        this.following = following;
    }
}
