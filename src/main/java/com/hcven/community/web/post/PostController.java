package com.hcven.community.web.post;

import com.hcven.community.data.common.CommonRes;
import com.hcven.community.service.PostService;
import com.hcven.community.vo.PostVO;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhanghao
 * @since 2019-03-12
 */
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = PostApiConsts.COMMUNITY_API_POST_GET_POST_ALL)
    @RequiresAuthentication
    public CommonRes getPosts() {
        CommonRes res = CommonRes.retOk();
        res.setData(postService.listPost());
        return res;
    }
}
