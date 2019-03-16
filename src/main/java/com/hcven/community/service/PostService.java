package com.hcven.community.service;

import com.hcven.community.vo.PostVO;

import java.util.List;

/**
 * @author zhanghao
 * @since 2019-03-12
 */
public interface PostService {
    List<PostVO> getPosts();
}
