package com.hcven.community.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author zhanghao
 * @since 2019-05-23
 */
@Document(value = "post_tag")
public class PostTag {
    @Id
    private String id;
    private Long postId;
    private List<String> postTag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public List<String> getPostTag() {
        return postTag;
    }

    public void setPostTag(List<String> postTag) {
        this.postTag = postTag;
    }
}
