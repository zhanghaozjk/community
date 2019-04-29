package com.hcven.community.dao.impl;

import com.hcven.community.dao.PostDAO;
import com.hcven.community.data.PostLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author zhanghao
 * @since 2019-04-27
 */
@Component
public class PostDAOImpl implements PostDAO {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void createPostLike(PostLike postLike) {
        mongoTemplate.save(postLike);
    }

    @Override
    public void savePostLike(Long postId, Long userId) {
        Query query = new Query((Criteria.where("postId")).is(postId));
        Update update = new Update();
        update.push("userId", userId);
        mongoTemplate.updateFirst(query, update, PostLike.class);
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
        PostLike postLike = mongoTemplate.findOne(query, PostLike.class);
        if (postLike != null) {
            return postLike.getUserId().size();
        }
        return 0;
    }

    @Override
    public Boolean userLikePost(Long postId, Long userId) {
        Query query = new Query(Criteria.where("postId").is(postId).
                andOperator(Criteria.where("userID").is(userId)));
        PostLike postLike = mongoTemplate.findOne(query, PostLike.class);
        return postLike != null;
    }
}
