package com.hcven.community.mapper;

import com.hcven.community.data.Post;
import java.util.List;
import java.util.Map;

public interface PostMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

    /**
     * 查询posts
     * @param params start count username status
     * @return list post
     */
    List<Post> listPost(Map<String, Object> params);

    /**
     *
     * 查询符合查询条件的posts的数量
     * @param params start count username status
     * @return count
     */
    Integer countPost(Map<String, Object> params);

    /**
     * 通过postId查询post
     * @param postIds
     * @return
     */
    List<Post> listByIds(List<Long> postIds);

    /**
     * 通过地理位置查询推荐话题
     * @param params location start count
     * @return
     */
    List<Post> listByHot(Map<String, Object> params);

    /**
     * 通过地理位置查询推荐话题count
     * @param params location start count
     * @return
     */
    Integer countByHot(Map<String, Object> params);
}