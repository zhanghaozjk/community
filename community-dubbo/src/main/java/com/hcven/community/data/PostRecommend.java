package com.hcven.community.data;

import java.util.Date;

public class PostRecommend {
    private Long id;

    private Long postId;

    private Double score;

    private Date createTime;

    private Date updateTime;

    private Integer lastLike;

    private Integer lastComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLastLike() {
        return lastLike;
    }

    public void setLastLike(Integer lastLike) {
        this.lastLike = lastLike;
    }

    public Integer getLastComment() {
        return lastComment;
    }

    public void setLastComment(Integer lastComment) {
        this.lastComment = lastComment;
    }
}