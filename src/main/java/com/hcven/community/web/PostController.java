package com.hcven.community.web;

import com.hcven.community.service.PostService;
import com.hcven.community.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhanghao
 * @since 2019-03-12
 */
@RestController
@RequestMapping(value = "/post")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {this.postService = postService;}

    @GetMapping(value = "/get")
    private List<PostVO> getPosts() {
        return postService.getPosts();
    }

    @GetMapping(value = "/get/top")
    private PostVO getPost() {
        return postService.getPosts().get(0);
    }
}
