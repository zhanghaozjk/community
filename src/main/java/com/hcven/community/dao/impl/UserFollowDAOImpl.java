package com.hcven.community.dao.impl;

import com.hcven.community.dao.UserFollowDAO;
import com.hcven.community.data.UserFollow;
import com.hcven.community.mapper.UserFollowMapper;
import com.hcven.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhanghao
 * @since 2019-05-09
 */
@Component
public class UserFollowDAOImpl implements UserFollowDAO {

    private final UserFollowMapper userFollowMapper;

    @Autowired
    public UserFollowDAOImpl(UserFollowMapper userFollowMapper) {this.userFollowMapper = userFollowMapper;}

    @Override
    public List<Long> getFollowerIdList(Long userId) {
        UserFollow userFollow = userFollowMapper.selectByPrimaryKey(userId);
        List<Long> ids = new ArrayList<>();
        if (userFollow == null) {
            return ids;
        }
        String followers = userFollow.getFollowers();
        if (StringUtils.isEmpty(followers)) {
            return ids;
        }
        String[] followerIdList = ",".split(followers);
        List<String> isStrings = Arrays.asList(followerIdList);
        // formatting
        isStrings.forEach(id -> {
            Long idL = ApplicationUtils.strFormattingLong(id);
            if (idL != null) {
                ids.add(idL);
            }
        });
        return ids;
    }

    @Override
    public List<Long> getFollowingIdList(Long userId) {
        UserFollow userFollow = userFollowMapper.selectByPrimaryKey(userId);
        List<Long> ids = new ArrayList<>();
        if (userFollow == null) {
            return ids;
        }
        String followings = userFollow.getFollowing();
        if (StringUtils.isEmpty(followings)) {
            return ids;
        }
        String[] followerIdList = ",".split(followings);
        List<String> isStrings = Arrays.asList(followerIdList);
        // formatting
        isStrings.forEach(id -> {
            Long idL = ApplicationUtils.strFormattingLong(id);
            if (idL != null) {
                ids.add(idL);
            }
        });
        return ids;
    }

    @Override
    public Integer getFollowerCount(Long userId) {
        return getFollowerIdList(userId).size();
    }

    @Override
    public Integer getFollowingCount(Long userId) {
        return getFollowingIdList(userId).size();
    }
}
