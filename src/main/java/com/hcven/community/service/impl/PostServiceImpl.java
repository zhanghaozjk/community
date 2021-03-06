package com.hcven.community.service.impl;

import com.hcven.community.dao.PostDAO;
import com.hcven.community.data.Comment;
import com.hcven.community.data.Post;
import com.hcven.community.data.PostComment;
import com.hcven.community.data.PostLike;
import com.hcven.community.data.PostTag;
import com.hcven.community.data.UserTag;
import com.hcven.community.dto.UserSecureData;
import com.hcven.community.mapper.PostMapper;
import com.hcven.community.service.PostRecommendService;
import com.hcven.community.service.PostService;
import com.hcven.community.service.UserService;
import com.hcven.community.vo.MineUserVO;
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
import org.springframework.util.CollectionUtils;

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

    private final PostDAO postDAO;

    @Autowired
    public PostServiceImpl(PostMapper postMapper, UserService userService, PostDAO postDAO) {this.postMapper = postMapper;
        this.userService = userService;
        this.postDAO = postDAO;
    }

    static class PostStatus {
        static Integer NORMAL = 1;
        static Integer DELETE = 0;
    }

    static class PostTagType {
        static String CAT = "cat";
        static String DOG = "dog";
    }

    @Override
    public List<PostVO> listPost(Long start, Integer count, String username, Boolean system) {
        Map<String, Object> params = new HashMap<>(8);
        params.put("start", start == null ? 0L : start);
        params.put("count", count == null ? 20 : count);
        params.put("username", username);
        params.put("status", PostStatus.NORMAL);
        List<Post> postDOs = postMapper.listPost(params);
        UserSecureData user;
        if (!system) {
            user = userService.getUser(SessionUtils.getUsername());
        } else {
            user = new UserSecureData();
            user.setId(1L);
        }
        return convertPostList2PostVOList(postDOs, user);
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
                PostLike postLike = new PostLike();
                PostTag postTag = new PostTag();
                postLike.setPostId(post.getId());
                postTag.setPostId(post.getId());
                postTag.setPostTag(postVO.getTags());
                PostComment postComment = new PostComment();
                postComment.setPostId(post.getId());
                postComment.setComment(new ArrayList<>());
                postDAO.createPostLike(postLike);
                postDAO.createPostComment(postComment);
                postDAO.createPostTag(postTag);
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


    @Override
    public MineUserVO getMineUserDetail(String username) {
        if (username == null) {
            username = SessionUtils.getUsername();
        }
        MineUserVO userVO = new MineUserVO();
        UserSecureData user = userService.getUser(username);
        // nickname
        userVO.setNickname(user.getNickname());
        Map<String, Object> postQueryMap = new HashMap<>(4);
        postQueryMap.put("username", username);
        postQueryMap.put("status", PostStatus.NORMAL);
        Integer postCount = postMapper.countPost(postQueryMap);
        // postCount
        userVO.setPostCount(postCount);
        // followers count
        userVO.setFollowersCount(userService.getFollowerCount(user.getId()));
        // following count
        userVO.setFollowingCount(userService.getFollowingCount(user.getId()));
        // todo description

        return userVO;
    }

    @Override
    public Boolean userLikePost(Long postId) {
        UserSecureData user = userService.getUser(SessionUtils.getUsername());
        try {
            postDAO.savePostLike(postId, user.getId());
        } catch (Exception e) {
            logger.error("user " + user.getUsername() + " like post error " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Boolean userUnlikePost(Long postId) {
        UserSecureData user = userService.getUser(SessionUtils.getUsername());
        try {
            postDAO.removePostLike(postId, user.getId());
        } catch (Exception e) {
            logger.error("user " + user.getUsername() + " unlike post error " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public PostComment getPostComment(Long postId) {
        PostComment postComment = postDAO.getPostComment(postId);
        // 更新一遍nickname
        for (Comment comment : postComment.getComment()) {
            comment.setNickname(userService.userGetNicknameByUserId(comment.getUserId()));
        }
        return postComment;
    }


    @Override
    public Boolean addPostComment(Long postId, String content) {
        Comment comment = new Comment();
        String username = SessionUtils.getUsername();
        UserSecureData userSecureData = userService.getUser(username);
        comment.setPostId(postId);
        comment.setDate(ApplicationUtils.getCurrentDateYMDHMS());
        comment.setUserId(userSecureData.getId());
        comment.setContent(content);
        try {
            postDAO.savePostComment(comment);
        } catch (Exception e) {
            logger.error("error at adding comment " + content);
            return false;
        }
        return true;
    }

    @Override
    public List<PostVO> listRecommendPost(String location, Long start, Integer count) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("start", start == null ? 0L : start);
        params.put("count", count == null ? 20 : count);
        params.put("location", location);
        params.put("status", PostStatus.NORMAL);
        List<Post> posts = postMapper.listByHot(params);
        UserSecureData user = userService.getUser(SessionUtils.getUsername());
        return convertPostList2PostVOList(posts, user);
    }

    @Override
    public Integer countRecommendPost(String location) {
        Map<String, Object> params = new HashMap<>(4);
        if (location != null) {
            params.put("location", location);
        }
        params.put("status", PostStatus.NORMAL);
        return postMapper.countByHot(params);
    }

    @Override
    public List<PostVO> getUserRecommend() {
        UserSecureData user = userService.getUser(SessionUtils.getUsername());
        UserTag userTag = userService.getUserTag(SessionUtils.getUsername());
        int catCount = userTag.getCatWeight() / 5;
        int dogCount = 20 - catCount;
        List<Long> postIds = new ArrayList<>();

        if (catCount > 0) {
            List<PostTag> catTag = postDAO.getPostByRandom(PostTagType.CAT, catCount);
            catTag.forEach(cat -> postIds.add(cat.getPostId()));
        }
        if (dogCount > 0) {
            List<PostTag> dogTag = postDAO.getPostByRandom(PostTagType.DOG, dogCount);
            dogTag.forEach(dog -> postIds.add(dog.getPostId()));
        }
        List<Post> posts = postMapper.listByIds(postIds);
        return convertPostList2PostVOList(posts, user);
    }

    @Override
    public void postTagInit() {
        // todo destory
        Map<String, Object> params = new HashMap<>(4);
        params.put("start", 0);
        params.put("count", 100);
        List<Post> posts = postMapper.listPost(params);
        posts.forEach(post -> {
            List<String> tags = new ArrayList<>();
            tags.add(post.getId() % 2 == 1 ? "cat" : "dog");
            PostTag postTag = new PostTag();
            postTag.setPostTag(tags);
            postTag.setPostId(post.getId());
            postDAO.createPostTag(postTag);
        });

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
        postVO.setLocation(post.getLocation());
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
        post.setLocation(postVO.getLocation());
        return post;
    }

    private List<PostVO> convertPostList2PostVOList(List<Post> posts, UserSecureData user) {
        List<PostVO> postVOList = new ArrayList<>();
        try {
            if (!CollectionUtils.isEmpty(posts)) {
                posts.forEach(post -> {
                    PostVO postVO = convertPost2PostVO(post);
                    if (postVO != null) {
                        if (post.getUsername() != null) {
                            postVO.setUserVO(postGetUserDetail(post.getUsername()));
                        }
                        postVO.setLikePost(postDAO.userLikePost(post.getId(), user.getId()));
                        postVO.setCommentCount(postDAO.countPostComment(post.getId()));
                        postVO.setLikeCount(postDAO.countPostLike(post.getId()));
                    }
                    postVOList.add(postVO);
                });
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return postVOList;
    }
}
