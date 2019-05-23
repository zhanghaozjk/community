package com.hcven.community.dao;

import com.hcven.community.data.Comment;
import com.hcven.community.data.PostComment;
import com.hcven.community.data.PostLike;
import com.hcven.community.data.PostTag;

import java.util.List;

/**
 * @author zhanghao
 * mongodb用dao
 * @since 2019-04-27
 */
public interface PostDAO {
    /**
     * 创建post的时候调用，添加一条mongo记录 点赞
     * @param postLike
     */
    void createPostLike(PostLike postLike);

    /**
     * 添加一个点赞
     * @param postId
     * @param userId
     */
    void savePostLike(Long postId, Long userId);

    /**
     * 用户取消点赞
     * @param postId
     * @param userId
     */
    void removePostLike(Long postId, Long userId);

    /**
     * 对某一条post 获取他的点赞数量
     * @param postId post在数据库中的id
     * @return 点赞数量
     */
    Integer countPostLike(Long postId);

    /**
     * 用户是否对某一条post已经点赞
     * @param postId
     * @param userId
     * @return
     */
    Boolean userLikePost(Long postId, Long userId);

    /**
     * mongodb中创建一个评论记录，创建post的时候调用
     * @param postComment
     */
    void createPostComment(PostComment postComment);

    /**
     * 保存一条用户评论
     * @param comment
     */
    void savePostComment(Comment comment);

    /**
     * todo
     * 删除一条用户评论
     * @param comment
     */
    void removePostComment(Comment comment);

    /**
     * 对某一个post，查询评论的数量
     * @param postId
     * @return
     */
    Integer countPostComment(Long postId);

    /**
     * 获取post的【全部】评论
     * @param postId
     * @return
     */
    PostComment getPostComment(Long postId);

    /**
     * 创建tag 新增一条post的时候调用
     * @param postTag
     */
    void createPostTag(PostTag postTag);

    /**
     * 保存tag
     * @param postId
     * @param tag
     */
    void savePostTag(Long postId, String tag);

    /**
     * 通过postId获得post tag
     * @param postId
     * @return
     */
    PostTag getPostTagById(Long postId);

    /**
     * 通过tag获得postID
     * @param tag
     * @param count
     * @return
     */
    List<PostTag> getPostByRandom(String tag, Integer count);
}
