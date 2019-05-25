package com.hcven.community.vo;

import java.util.List;

/**
 * @author zhanghao
 * @since 2019-03-12
 */
public class PostVO {
    /**
     * 话题id
     */
    private Long id;
    /**
     * 发表日期
     */
    private String date;
    /**
     * 用户
     */
    private UserVO userVO;
    /**
     * 可见列表
     */
    private List<Long> unVisible;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片列表
     */
    private List<Long> imageIds;
    /**
     * 状态 0-delete 1-normal
     */
    private Integer status;
    /**
     * 是否点赞
     */
    private Boolean likePost;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 发送位置
     */
    private String location;

    /**
     * tags
     */
    private List<String> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public List<Long> getUnVisibleId() {
        return unVisible;
    }

    public void setUnVisibleId(List<Long> unVisible) {
        this.unVisible = unVisible;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Long> imageIds) {
        this.imageIds = imageIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getLikePost() {
        return likePost;
    }

    public void setLikePost(Boolean likePost) {
        this.likePost = likePost;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
