package com.hcven.community.service.impl;

import com.hcven.CommunityApplication;
import com.hcven.community.service.PostRecommendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhanghao
 * @since 2019-05-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class PostRecommendServiceImplTest {

    @Autowired
    PostRecommendService postRecommendService;

    @Test
    public void insertOrUpdateRecommend() {
        postRecommendService.insertOrUpdateRecommend();
    }
}