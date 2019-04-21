package com.hcven.community.data;

import java.util.Date;

public class Post {
    private Long id;

    private Date date;

    private String username;

    private String unVisible;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUnVisibleId() {
        return unVisible;
    }

    public void setUnVisibleId(String unVisible) {
        this.unVisible = unVisible;
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