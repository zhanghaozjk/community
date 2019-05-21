package com.hcven.community.scheduled;

import com.hcven.community.mapper.PostRecommendMapper;
import com.hcven.community.service.PostRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author zhanghao
 * @since 2019-05-21
 */
@Component
public class PostRecommendSchedule {

    private final PostRecommendService postRecommendService;

    @Autowired
    public PostRecommendSchedule(PostRecommendService postRecommendService) {this.postRecommendService = postRecommendService;}

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void submitPostRecommend() {
        postRecommendService.insertOrUpdateRecommend();
    }
}
