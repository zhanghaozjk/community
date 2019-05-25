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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        map.put("postVoList", postService.listPost(start, count, username, false));
        map.put("count", postService.countPost(username));
        res.setData(map);
        return res;
    }

    @PostMapping(value = PostApiConsts.COMMUNITY_API_POST_ALL_HOT)
    @RequiresAuthentication
    public CommonRes getPostsHot(@RequestParam(value = "location", required = false) String location,
                                 @RequestParam(value = "start", required = false) Long start,
                                 @RequestParam(value = "count", required = false) Integer count) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("postVoList", postService.listRecommendPost(location, start, count));
        map.put("count", postService.countRecommendPost(location));
        return CommonRes.retOk(map);
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

    @PostMapping(value = PostApiConsts.COMMUNITY_API_POST_USER_INFORMATION)
    @RequiresAuthentication
    public CommonRes userDetails(@RequestParam(value = "username", required = false)String username) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("mineUserVO", postService.getMineUserDetail(username));
        return CommonRes.retOk(data);
    }

    @PostMapping(value = PostApiConsts.COMMUNITY_API_POST_ALL_USER_RECOMMEND)
    @RequiresAuthentication
    public CommonRes getUserRecommend() {
        Map<String, Object> data = new HashMap<>(4);
        data.put("postVoList", postService.getUserRecommend());
        return CommonRes.retOk(data);
    }

    @DeleteMapping(value = PostApiConsts.COMMUNITY_API_POST_DELETE_POST)
    @RequiresAuthentication
    public CommonRes deletePost(@RequestParam("id")Long postId) {
        Map<String, Object> data = postService.deletePost(postId);
        return CommonRes.retOk(data);
    }

    @PostMapping(value = PostApiConsts.COMMUNITY_API_POST_LIKE_POST)
    @RequiresAuthentication
    public CommonRes likePost(@RequestParam("id")Long postId) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("success", postService.userLikePost(postId));
        return CommonRes.retOk(data);
    }

    @PostMapping(value = PostApiConsts.COMMUNITY_API_POST_UNLIKE_POST)
    @RequiresAuthentication
    public CommonRes unlikePost(@RequestParam("id")Long postId) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("success", postService.userUnlikePost(postId));
        return CommonRes.retOk(data);
    }

    @GetMapping(value = PostApiConsts.COMMUNITY_API_POST_COMMENT_GET)
    @RequiresAuthentication
    public CommonRes getComment(@PathVariable(value = "id") Long postId) {
        Map<String, Object> data = new HashMap<>(4);
        data.put("postComment", postService.getPostComment(postId));
        return CommonRes.retOk(data);
    }

    @PutMapping(value = PostApiConsts.COMMUNITY_API_POST_COMMENT_ADD)
    @RequiresAuthentication
    public CommonRes addComment(@RequestParam("postId") Long postId, @RequestParam("comment") String comment) {
        Boolean success = postService.addPostComment(postId, comment);
        Map<String, Object> data = new HashMap<>(4);
        data.put("success", success);
        return CommonRes.retOk(data);
    }


    @PostMapping(value = PostApiConsts.COMMUNITY_PRIVATE_POST_TAG_INIT)
    public CommonRes postTagInit(@RequestParam("postTag")Boolean truth) {
        if (truth) {
            postService.postTagInit();
            return CommonRes.retOk();
        }
        return CommonRes.retOk("something wrong");
    }
}
