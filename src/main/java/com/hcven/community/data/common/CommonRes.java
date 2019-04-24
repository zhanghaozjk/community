package com.hcven.community.data.common;

/**
 * @author zhanghao
 * @since 2019-03-18
 */
public class CommonRes {

    /**
     * http 状态码
     */
    private int code;

    /**
     * 返回错误信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;

    public CommonRes(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static CommonRes retOk() {
        return new CommonRes(200, null, null);
    }
    public static CommonRes retOk(Object data) {
        return new CommonRes(200, null, data);
    }

    public static CommonRes message(String msg) {
        return new CommonRes(200, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
