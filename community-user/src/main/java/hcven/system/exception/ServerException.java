package com.hcven.system.exception;

/**
 * @author zhanghao
 * @since 2019-04-13
 */
public class ServerException extends RuntimeException {
    public ServerException(String msg) {
        super(msg);
    }

    public ServerException() {
        super();
    }
}