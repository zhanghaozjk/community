package com.hcven.community.service.impl;

import com.hcven.CommunityApplication;
import com.hcven.community.mapper.PostRecommendMapper;
import com.hcven.community.service.PostRecommendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-05-21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class PostRecommendServiceImplTest {

    @Autowired
    PostRecommendService postRecommendService;

    @Autowired
    PostRecommendMapper postRecommendMapper;

    @Test
    public void insertOrUpdateRecommend() {
        postRecommendService.insertOrUpdateRecommend();
    }

    @Test
    public void getPostIds() {
        Map<String, Object> map = new HashMap<>();
        map.put("start", 0);
        map.put("count", 50);
        List<Long> ids = postRecommendService.getHotPostIds(map);
        ids.forEach(System.out::println);
    }
}