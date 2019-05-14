package com.hcven.community.data;

import java.io.Serializable;

public class UserTag implements Serializable {
    private Integer id;

    private Long userId;

    private Integer catWeight;

    private Integer dogWeight;

    private Integer otherWeight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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