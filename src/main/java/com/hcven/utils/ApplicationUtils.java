package com.hcven.utils;

import org.apache.commons.lang3.RandomStringUtils;
/**
 * @author zhanghao
 * @since 2019-03-14
 */
public class ApplicationUtils {
    /**
     * 生成一个count长的字符串
     * @param count 字符串长度
     * @return
     */
    public static String getNumStringRandom(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
