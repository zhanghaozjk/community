package com.hcven.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhanghao
 * @since 2019-03-14
 */
public class ApplicationUtils {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);

    private static final String YMD_FORMAT = "yyyy-MM-dd";
    /**
     * 生成一个count长的字符串
     * @param count 字符串长度
     * @return
     */
    public static String getNumStringRandom(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    /**
     * YYYY-MM-DD
     * @param date
     * @return
     */
    public static String getYMD(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YMD_FORMAT);
        return date == null ? null : dateFormat.format(date);
    }

    public static Date ymdGetDate(String ymd) {
        if (ymd == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(YMD_FORMAT);
        Date date = null;
        try {
            date = dateFormat.parse(ymd);
        } catch (ParseException e) {
            logger.error("formatting " + ymd + " date error");
        }
        return date;
    }

    public static Long strFormattingLong(String num) {
        try {
            return Long.valueOf(num);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
