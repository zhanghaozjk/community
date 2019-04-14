package com.hcven.community.service.impl;

import com.hcven.community.service.PostService;
import com.hcven.community.vo.PostVO;
import com.hcven.community.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhanghao
 * @since 2019-03-12
 */

@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<PostVO> listPost() {
        PostVO vo = new PostVO();
        UserVO userVO = new UserVO();
        userVO.setId(90001L);
        userVO.setNickname("郑好奇");
        vo.setId(100000L);
        vo.setDate(new Date());
        vo.setContent("今天是个好天气啊");
        vo.setUserVO(userVO);
        List<PostVO> postVOS = new ArrayList<>();
        postVOS.add(vo);
        return postVOS;
    }
}
