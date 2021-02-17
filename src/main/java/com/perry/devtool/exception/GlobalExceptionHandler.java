package com.perry.devtool.exception;

import com.perry.devtool.common.ApiRestResponse;
import com.perry.devtool.enums.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Perry
 * @date 2020/12/3
 */
@ControllerAdvice//拦截异常
public class GlobalExceptionHandler {
    private final Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e) {
        log.error("Default Exception:"+e);
        return ApiRestResponse.error(ExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public Object handlerCommonException(CommonException e) {
        log.error("CommonException:"+e);
        return ApiRestResponse.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: ", e);
        return handleBindingResult(e.getBindingResult());
    }

    private ApiRestResponse handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0) {
            return ApiRestResponse.error(ExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse
                .error(ExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }

}
