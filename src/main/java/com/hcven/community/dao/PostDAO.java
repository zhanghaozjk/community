package com.hcven.community.dao;

import com.hcven.community.data.PostLike;

/**
 * @author zhanghao
 * @since 2019-04-27
 */
public interface PostDAO {
    void createPostLike(PostLike postLike);

    void savePostLike(Long postId, Long userId);

    void removePostLike(Long postId, Long userId);

    Integer countPostLike(Long postId);

    Boolean userLikePost(Long postId, Long userId);
}
