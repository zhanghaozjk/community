package com.hcven.community.mapper;

import com.hcven.community.data.UserFollow;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserFollow record);

    int insertSelective(UserFollow record);

    UserFollow selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserFollow record);

    int updateByPrimaryKey(UserFollow record);
}