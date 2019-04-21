package com.hcven.community.service;

import com.hcven.community.vo.PostVO;

import java.util.List;
import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-03-12
 */
public interface PostService {

    /**
     * 获得Post列表
     * @param start
     * @param count
     * @param username
     * @return
     */
    List<PostVO> listPost(Long start, Integer count, String username);

    /**
     * 新增一条post
     * @param post
     * @return
     */
    Boolean addPost(PostVO post);

    /**
     * 删除一条post
     * @param id
     * @return
     */
    Map<String, Object> deletePost(Long id);
}
