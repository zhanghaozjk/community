package com.hcven.community.dao.impl;

import com.hcven.community.dao.PostDAO;
import com.hcven.community.data.Comment;
import com.hcven.community.data.PostComment;
import com.hcven.community.data.PostLike;
import com.hcven.community.data.PostTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * @author zhanghao
 * @since 2019-04-27
 */
@Component
public class PostDAOImpl implements PostDAO {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public PostDAOImpl(MongoTemplate mongoTemplate) {this.mongoTemplate = mongoTemplate;}

    @Override
    public void createPostLike(PostLike postLike) {
        if (postLike.getUserId() == null) {
            postLike.setUserId(new ArrayList<>());
        }
        mongoTemplate.save(postLike);
    }

    @Override
    public void savePostLike(Long postId, Long userId) {
        if (!userLikePost(postId, userId)) {
            createPostLikeIfNotExistsRecord(postId);
            Query query = new Query((Criteria.where("postId")).is(postId));
            Update update = new Update();
            update.push("userId", userId);
            mongoTemplate.updateFirst(query, update, PostLike.class);
        }
    }

    @Override
    public void removePostLike(Long postId, Long userId) {
        Query query = new Query((Criteria.where("postId")).is(postId));
        Update update = new Update();
        update.pull("userId", userId);
        mongoTemplate.updateFirst(query, update, PostLike.class);
    }

    @Override
    public Integer countPostLike(Long postId) {
        Query query = new Query((Criteria.where("postId")).is(postId));
        createPostLikeIfNotExistsRecord(postId);
        PostLike postLike = mongoTemplate.findOne(query, PostLike.class);
        if (postLike != null) {
            if (postLike.getUserId() != null) {
                return postLike.getUserId().size();
            }
        }
        return 0;
    }

    @Override
    public Boolean userLikePost(Long postId, Long userId) {
        Query query = new Query(Criteria.where("postId").is(postId).
                andOperator(Criteria.where("userId").all(userId)));
        PostLike postLike = mongoTemplate.findOne(query, PostLike.class);
        return postLike != null;
    }

    @Override
    public void createPostComment(PostComment postComment) {
        if (postComment!= null && postComment.getPostId() != null) {
            if (postComment.getComment() == null) {
                List<Comment> comments = new ArrayList<>();
                postComment.setComment(comments);
            }
            mongoTemplate.save(postComment);
        }
    }

    @Override
    public void savePostComment(Comment comment) {
        if (comment != null) {
            createPostCommentIfNotExistsRecord(comment.getPostId());
            PostComment postComment = getPostComment(comment.getPostId());
            if (postComment != null) {
                Update update = new Update();
                update.push("comment", comment);
                mongoTemplate.updateFirst(getPostCommentQuery(comment.getPostId()), update, PostComment.class);
            }
        }
    }

    @Override
    public void removePostComment(Comment comment) {
        // todo 删除评论
    }

    @Override
    public Integer countPostComment(Long postId) {
        PostComment postComment = getPostComment(postId);
        return (postComment != null && postComment.getComment() != null) ?
                postComment.getComment().size() : 0;
    }

    @Override
    public PostComment getPostComment(Long postId) {
        createPostCommentIfNotExistsRecord(postId);
        Query query = new Query((Criteria.where("postId").is(postId)));
        return mongoTemplate.findOne(query, PostComment.class);
    }

    @Override
    public void createPostTag(PostTag postTag) {
        if (postTag.getPostTag() == null) {
            postTag.setPostTag(new ArrayList<>());
        }
        mongoTemplate.save(postTag);
    }

    @Override
    public void savePostTag(Long postId, String postTag) {
        if (postId != null && !StringUtils.isEmpty(postTag)) {
            Query query = new Query(Criteria.where("postId").is(postId));
            Update update = new Update();
            update.push("postTag", postTag);
            mongoTemplate.updateFirst(query, update, PostTag.class);
        }
    }

    @Override
    public PostTag getPostTagById(Long postId) {
        Query query = new Query(Criteria.where("postId").is(postId));
        return mongoTemplate.findOne(query, PostTag.class);
    }

    @Override
    public List<PostTag> getPostByRandom(String tag, Integer count) {
        Aggregation agg = newAggregation(
                Aggregation.match(Criteria.where("postTag").all(tag)),
                Aggregation.sample(count));
        AggregationResults<PostTag> postTags = mongoTemplate.aggregate(agg, "post_tag", PostTag.class);
        return postTags.getMappedResults();
    }

    private Query getPostCommentQuery(Long postId) {
        return new Query((Criteria.where("postId").is(postId)));
    }

    /**
     * 对之前版本的更新
     * @param postId
     */
    private void createPostLikeIfNotExistsRecord(Long postId) {
        Query query = new Query((Criteria.where("postId")).is(postId));
        PostLike postLike = mongoTemplate.findOne(query, PostLike.class);
        if (postLike == null) {
            postLike = new PostLike();
            postLike.setPostId(postId);
            createPostLike(postLike);
        }
    }

    /**
     * 对之前对版本更新
     * @param postId
     */
    private void createPostCommentIfNotExistsRecord(Long postId) {
        Query query = new Query((Criteria.where("postId").is(postId)));
        PostComment postComment = mongoTemplate.findOne(query, PostComment.class);
        if (postComment == null) {
            postComment = new PostComment();
            postComment.setPostId(postId);
            postComment.setComment(new ArrayList<>());
            createPostComment(postComment);
        }
    }
}
