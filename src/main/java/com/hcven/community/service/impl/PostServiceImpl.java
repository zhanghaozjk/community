package com.hcven.community.service.impl;

import com.hcven.community.data.Post;
import com.hcven.community.dto.UserSecureData;
import com.hcven.community.mapper.PostMapper;
import com.hcven.community.service.PostService;
import com.hcven.community.service.UserService;
import com.hcven.community.vo.PostVO;
import com.hcven.community.vo.UserVO;
import com.hcven.system.exception.ServerException;
import com.hcven.system.exception.UnauthorizedException;
import com.hcven.utils.ApplicationUtils;
import com.hcven.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanghao
 * @since 2019-03-12
 */

@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostMapper postMapper;

    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostMapper postMapper, UserService userService) {this.postMapper = postMapper;
        this.userService = userService;
    }

    private static class PostStatus {
        static Integer NORMAL = 1;
        static Integer DELETE = 0;
    }

    @Override
    public List<PostVO> listPost(Long start, Integer count, String username) {
        List<PostVO> posts = new ArrayList<>();

        Map<String, Object> params = new HashMap<>(8);
        params.put("start", start == null ? 0L : start);
        params.put("count", count == null ? 20 : count);
        params.put("username", username);
        params.put("status", PostStatus.NORMAL);
        List<Post> postDOs = postMapper.listPost(params);
        try {
            postDOs.forEach(post -> {
                PostVO postVO = convertPost2PostVO(post);
                if (postVO != null) {
                    if (post.getUsername() != null) {
                        postVO.setUserVO(postGetUserDetail(post.getUsername()));
                    }
                }
                posts.add(postVO);
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return posts;
    }

    @Override
    public Integer countPost(String username) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("status", PostStatus.NORMAL);
        params.put("username", username);

        return postMapper.countPost(params);
    }


    @Override
    public Boolean addPost(PostVO postVO) {
        if (postVO == null || SessionUtils.getUsername() == null) {
            return false;
        }
        String username = SessionUtils.getUsername();

        Post post = convertPostVO2Post(postVO, username);
        post.setStatus(PostStatus.NORMAL);
        try {
            if (post.getId() != null) {
                postMapper.updateByPrimaryKeySelective(post);
            } else {
                postMapper.insert(post);
            }
        } catch (Exception e) {
            String message = String.format("User %s adding or updating a post error", username);
            logger.error(message);
            throw new ServerException(message);
        }
        return true;
    }

    @Override
    public Map<String, Object> deletePost(Long id) {
        Map<String, Object> map = new HashMap<>(4);
        if (id == null) {
            map.put("msg", "Post id cannot be null");
            return map;
        }
        Post post = postMapper.selectByPrimaryKey(id);
        if (post == null) {
            map.put("msg", String.format("Post %d of id cannot be found", id));
            return map;
        }
        String username = SessionUtils.getUsername();
        if (!post.getUsername().equals(username)) {
            String msg = String.format("User %s has no right to delete post %d", username, id);
            map.put("msg", msg);
            throw new UnauthorizedException(msg);
        } else {
            Post update = new Post();
            update.setId(id);
            update.setStatus(PostStatus.DELETE);
            postMapper.updateByPrimaryKeySelective(update);
            map.put("success", true);
        }
        return map;
    }

    private UserVO postGetUserDetail(String username) {
        if (username == null) {
            return null;
        }
        UserVO user = new UserVO();
        UserSecureData data = userService.getUser(username);
        if (data != null) {
            user.setNickname(data.getNickname());
            return user;
        }
        return null;
    }

    private PostVO convertPost2PostVO(Post post) {
        if (post == null) {
            return null;
        }
        PostVO postVO = new PostVO();
        postVO.setId(post.getId());
        postVO.setContent(post.getContent());;
        postVO.setDate(ApplicationUtils.getYMD(post.getDate()));
        return postVO;
    }

    private Post convertPostVO2Post(PostVO postVO, String username) {
        if (postVO == null || username == null) {
            return null;
        }
        Post post = new Post();
        post.setId(postVO.getId());
        post.setContent(postVO.getContent());
        Date date = ApplicationUtils.ymdGetDate(postVO.getDate());
        // 前端不传date || update post
        post.setDate(date == null ? new Date(System.currentTimeMillis()) : date);
        post.setUsername(username);
        return post;
    }
}
