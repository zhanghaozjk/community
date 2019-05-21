package com.hcven.community.service.impl;

import com.hcven.community.algorithm.CoolingLaw;
import com.hcven.community.data.Post;
import com.hcven.community.data.PostRecommend;
import com.hcven.community.mapper.PostRecommendMapper;
import com.hcven.community.service.PostRecommendService;
import com.hcven.community.service.PostService;
import com.hcven.community.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhanghao
 * @since 2019-05-21
 */
@Service
public class PostRecommendServiceImpl implements PostRecommendService {

    private final PostRecommendMapper postRecommendMapper;

    private final PostService postService;

    private static final Integer QUERY_COUNT = 20;

    private static Double averageScore = -1D;

    private static final Double BASE_SCORE = 50D;

    @Autowired
    public PostRecommendServiceImpl(PostRecommendMapper postRecommendMapper, PostService postService) {this.postRecommendMapper = postRecommendMapper;
        this.postService = postService;
    }

    @Override
    public void insertOrUpdateRecommend() {
        Integer postCount = postService.countPost(null);
        List<PostVO> postVOList = new ArrayList<>();
        for (Long i = 0L; i < postCount; i += QUERY_COUNT) {
            postVOList.addAll(postService.listPost(i, QUERY_COUNT, null, true));
        }
        List<PostRecommend> postRecommends = new ArrayList<>();
        postVOList.forEach(postVO -> {
            PostRecommend postRecommend = getPostRecommendByPostId(postVO.getId());
            if (postRecommend == null) {
                PostRecommend recommend = getInitPostRecommend(postVO);
                insertPostRecommend(recommend);
            } else {
                updatePostRecommend(postRecommend, postVO);
            }
        });
    }

    /**
     * 更新
     * @param postRecommend
     * @param postVO
     */
    private void updatePostRecommend(PostRecommend postRecommend, PostVO postVO) {
        Integer likeDiff = postVO.getLikeCount() - postRecommend.getLastLike();
        Integer commentDiff = postVO.getCommentCount() - postRecommend.getLastComment();
        Double currentScore = CoolingLaw.getCurrentTemp(postRecommend.getScore(), likeDiff, commentDiff);
        postRecommend.setScore(currentScore);
        postRecommend.setUpdateTime(new Date(System.currentTimeMillis()));
        postRecommend.setLastLike(postVO.getLikeCount());
        postRecommend.setLastComment(postVO.getCommentCount());
        postRecommendMapper.updateByPrimaryKeySelective(postRecommend);
    }

    /**
     * 初始化
     * @param postVO
     * @return
     */
    private PostRecommend getInitPostRecommend(PostVO postVO) {
        PostRecommend recommend = new PostRecommend();
        recommend.setPostId(postVO.getId());
        recommend.setCreateTime(new Date(System.currentTimeMillis()));
        recommend.setScore(CoolingLaw.getCurrentTemp(getAverageScore(), postVO.getLikeCount(), postVO.getCommentCount()));
        recommend.setLastComment(postVO.getCommentCount());
        recommend.setLastLike(postVO.getLikeCount());
        recommend.setUpdateTime(new Date(System.currentTimeMillis()));
        return recommend;
    }

    private PostRecommend getPostRecommendByPostId(Long postId) {
        return postRecommendMapper.selectByPostId(postId);
    }

    private void insertPostRecommend(PostRecommend recommend) {
        postRecommendMapper.insert(recommend);
    }

    /**
     * 当前的平均值
     * @return
     */
    private Double getAverageScore() {
        if (averageScore <= 0) {
            averageScore = postRecommendMapper.averageScore();
            if (averageScore == null || averageScore <= BASE_SCORE) {
                averageScore = BASE_SCORE;
            }
        }
        return averageScore;
    }
}
