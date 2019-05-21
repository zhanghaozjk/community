package com.hcven.community.algorithm;

/**
 * 牛顿热力学算法
 * T = T0 * e ^ -a(t - t0)
 * @author zhanghao
 * @since 2019-05-21
 */
public class CoolingLaw {
    /**
     * K = -a(t - t0)
     * 假设T0 = 100，24h后T = 1
     * K = 0.192
     */
    private static final Double K_NUMBER = -0.192d;

    /**
     * 自然对数e = 2.718
     */
    private static final Double EULER  = 2.718d;

    /**
     * EULER_EXP_K_NUMBER = e ^ -a(t - t0)
     */
    private static final Double EULER_EXP_K_NUMBER = Math.pow(EULER, K_NUMBER);

    /**
     * 点赞热度上升参数
     */
    private static final Double K_LIKE = 0.8d;

    /**
     * 评论热度上升参数
     */
    private static final Double K_COMMENT = 1.1d;

    private static Double getCurrentTemp(Double beforeTemp) {
        return beforeTemp * EULER_EXP_K_NUMBER;
    }

    /**
     *
     * @param beforeTemp T0
     * @param likeDiff 现在时刻与之前的like数量差值
     * @param commentDiff 现在时刻与之前的comment差值
     * @return T
     */
    public static Double getCurrentTemp(Double beforeTemp, Integer likeDiff, Integer commentDiff) {
        return getCurrentTemp(beforeTemp) + likeDiff * K_LIKE + commentDiff * K_COMMENT;
    }
}
