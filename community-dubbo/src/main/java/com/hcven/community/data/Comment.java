package com.hcven.community.data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhanghao
 * @since 2019-05-13
 */
public class Comment implements Serializable {
    private Long id;
    private Long postId;
    private String nickname;
    private Long userId;
    private String content;
    private Date date;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" + "\'id\':" + id + "," + "\'postId\':" + postId + "," + "\'nickname\':\'" + nickname + '\'' + ","
                + "\'userId\':" + userId + "," + "\'content\':\'" + content + '\'' + "," + "\'date\':" + date + '}';
    }
}
