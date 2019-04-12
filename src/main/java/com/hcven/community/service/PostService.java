package com.hcven.community.service;

import com.hcven.community.vo.PostVO;

import java.util.List;

/**
 * @author zhanghao
 * @since 2019-03-12
 */
public interface PostService {
    /**
     * 获得Post列表
     * @return post视图
     */
    List<PostVO> listPost();


}
