package com.hcven.community.mapper;

import com.hcven.community.data.Post;
import org.apache.ibatis.annotations.Param;

import javax.naming.ldap.HasControls;
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
}