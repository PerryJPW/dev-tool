package com.perry.devtool.exception;

import com.perry.devtool.enums.ExceptionEnum;

/**
 * @author Perry
 * @date 2020/12/2
 */
public class CommonException extends RuntimeException {
    private final Integer code;
    private final String message;
    public CommonException(Integer code, String message){
        this.code=code;
        this.message=message;
    }
    public CommonException(ExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
