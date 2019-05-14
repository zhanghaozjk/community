package com.hcven.community.vo;

import java.io.Serializable;

/**
 * @author zhanghao
 * @since 2019-05-15
 */
public class UserInformationVO implements Serializable {
    private String username;
    private String nickname;
    private Integer catWeight;
    private Integer dogWeight;
    private Integer otherWeight;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getCatWeight() {
        return catWeight;
    }

    public void setCatWeight(Integer catWeight) {
        this.catWeight = catWeight;
    }

    public Integer getDogWeight() {
        return dogWeight;
    }

    public void setDogWeight(Integer dogWeight) {
        this.dogWeight = dogWeight;
    }

    public Integer getOtherWeight() {
        return otherWeight;
    }

    public void setOtherWeight(Integer otherWeight) {
        this.otherWeight = otherWeight;
    }
}
