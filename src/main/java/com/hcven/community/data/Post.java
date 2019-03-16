package com.hcven.community.data;

import java.util.Date;

public class Post {
    private Long id;

    private Date date;

    private Long userId;

    private String unVisibleId;

    private String content;

    private String images;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUnVisibleId() {
        return unVisibleId;
    }

    public void setUnVisibleId(String unVisibleId) {
        this.unVisibleId = unVisibleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}