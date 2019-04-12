package com.hcven.identify;

import com.hcven.community.data.common.CommonRes;
import com.hcven.system.exception.UnauthorizedException;
import org.apache.shiro.ShiroException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhanghao
 * @since 2019-03-14
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * shiro 异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public CommonRes handle401(ShiroException e) {
        return new CommonRes(401, e.getMessage(), null);
    }

    /**
     * 捕捉UnauthorizedException
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public CommonRes handle401() {
        return new CommonRes(401, "Unauthorized", null);
    }

    /**
     * 捕捉其他所有异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonRes globalException(HttpServletRequest request, Throwable ex) {
        return new CommonRes(getStatus(request).value(), ex.getMessage(), null);
    }

    /**
     * 获取返回的状态码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

