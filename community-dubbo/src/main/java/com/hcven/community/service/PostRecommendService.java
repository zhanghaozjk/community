package com.hcven.community.service;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-05-21
 */
public interface PostRecommendService {
    /**
     * 热度排序计算 用于schedule
     */
    void insertOrUpdateRecommend();

    /**
     * 获得热门id
     * @param params
     * @return
     */
    List<Long> getHotPostIds(Map<String, Object> params);
}
