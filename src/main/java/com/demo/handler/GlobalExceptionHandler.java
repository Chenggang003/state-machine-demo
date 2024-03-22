package com.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理所有其他异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse handleAllOtherExceptions(Exception ex, WebRequest request) {
        // 你可以在这里记录异常信息，比如使用日志框架
        BaseResponse baseResponse = new BaseResponse("-1", ex.getMessage(), null);
        return baseResponse;
    }
}