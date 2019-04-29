package com.hcven.community.dao.impl;

import com.hcven.community.dao.PostDAO;
import com.hcven.community.data.PostLike;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author zhanghao
 * @since 2019-04-27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostDAOImplTest {

    @Autowired
    PostDAO postDAO;

    @Autowired
    private MongoTemplate mongoTemplate;

    PostLike postLike;

    @Before
    public void init() {
        postLike = new PostLike();
        postLike.setPostId(10000L);
        postLike.setUserId(new ArrayList<>());
    }

    @Test
    public void save() {
        postDAO.createPostLike(postLike);
    }

    @Test
    public void savePostLike() {
        postDAO.savePostLike(10000L, 20000L);
        Query query = new Query((Criteria.where("postId")).is(10000L));
        PostLike postLike = mongoTemplate.findOne(query, PostLike.class);
        System.out.println(postLike.getPostId() + " " + postLike.getUserId());
    }

    @Test
    public void removePostLike() {
        postDAO.removePostLike(10000L, 20000L);
        Query query = new Query((Criteria.where("postId")).is(10000L));
        PostLike postLike = mongoTemplate.findOne(query, PostLike.class);
        System.out.println(postLike.getPostId() + " " + postLike.getUserId());
    }

    @Test
    public void countPostLike() {
        Integer count = postDAO.countPostLike(10000L);
        System.out.println(count);
    }

    @Test
    public void userLikePost() {
        Boolean truth = postDAO.userLikePost(10000L, 20000L);
        System.out.println(truth);
    }
}