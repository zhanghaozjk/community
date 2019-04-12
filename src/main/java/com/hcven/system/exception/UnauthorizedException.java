package com.hcven.system.exception;

/**
 * @author zhanghao
 * @since 2019-03-29
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super();
    }
}