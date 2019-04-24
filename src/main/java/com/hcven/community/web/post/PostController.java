package com.hcven.community.web.post;

import com.hcven.community.data.common.CommonRes;
import com.hcven.community.service.PostService;
import com.hcven.community.vo.PostVO;
import com.hcven.system.exception.ServerException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-03-12
 */
@RestController
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {this.postService = postService;}

    @PostMapping(value = PostApiConsts.COMMUNITY_API_POST_GET_POST_ALL)
    @RequiresAuthentication
    public CommonRes getPosts(@RequestParam(value = "start", required = false) Long start,
                              @RequestParam(value = "count", required = false) Integer count,
                              @RequestParam(value = "username", required = false) String username) {
        CommonRes res = CommonRes.retOk();
        Map<String, Object> map = new HashMap<>(4);
        map.put("postVoList", postService.listPost(start, count, username));
        map.put("count", postService.countPost(username));
        res.setData(map);
        return res;
    }

    @PutMapping(value = PostApiConsts.COMMUNITY_API_POST_PUT_NEW_POST)
    @RequiresAuthentication
    public CommonRes putNewPost(@RequestBody PostVO postVO) {
        Map<String, Boolean> data = new HashMap<>(4);
        try {
            postService.addPost(postVO);
            data.put("success", true);
        } catch (ServerException e) {
            data.put("success", false);
        }
        return CommonRes.retOk(data);
    }

    @DeleteMapping(value = PostApiConsts.COMMUNITY_API_POST_DELETE_POST)
    @RequiresAuthentication
    public CommonRes deletePost(@RequestParam("id")Long postId) {
        Map<String, Object> data = postService.deletePost(postId);
        return CommonRes.retOk(data);
    }
}
