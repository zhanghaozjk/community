package com.hcven.community.mapper;

import com.hcven.community.data.PostRecommend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostRecommendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PostRecommend record);

    int insertSelective(PostRecommend record);

    PostRecommend selectByPrimaryKey(Long id);

    PostRecommend selectByPostId(Long postId);

    int updateByPrimaryKeySelective(PostRecommend record);

    int updateByPrimaryKey(PostRecommend record);

    Double averageScore();

    List<Long> listHotPost(Map<String, Object> params);
}