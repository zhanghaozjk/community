package com.hcven.community.dao;

import java.util.List;

/**
 * @author zhanghao
 * @since 2019-05-09
 */
public interface UserFollowDAO {
    /**
     * 获取受关注id的list
     * @param userId 被查询的用户ID
     * @return
     */
    List<Long> getFollowerIdList(Long userId);

    /**
     * 获取正在关注id的list
     * @param userId 被查询的用户id
     * @return
     */
    List<Long> getFollowingIdList(Long userId);

    /**
     * 获取受关注id的count
     * @param userId 被查询的用户id
     * @return
     */
    Integer getFollowerCount(Long userId);

    /**
     * 获取正在关注id的count
     * @param userId 被查询的用户id
     * @return
     */
    Integer getFollowingCount(Long userId);
}
