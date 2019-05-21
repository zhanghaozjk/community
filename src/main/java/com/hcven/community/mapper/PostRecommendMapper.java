package com.hcven.community.mapper;

import com.hcven.community.data.PostRecommend;

public interface PostRecommendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PostRecommend record);

    int insertSelective(PostRecommend record);

    PostRecommend selectByPrimaryKey(Long id);

    PostRecommend selectByPostId(Long postId);

    int updateByPrimaryKeySelective(PostRecommend record);

    int updateByPrimaryKey(PostRecommend record);

    Double averageScore();
}