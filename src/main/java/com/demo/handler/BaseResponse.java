package com.demo.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: devil.chen
 * @Date: 2024/3/19 12:20
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    /**
     * 结果码
     */
    private String code;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 内容
     */
    private T data;

    /**
     * 成功
     *
     * @param data 内容
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("0000", "success", data);
    }

    /**
     * 成功
     * @return
     */
    public static  BaseResponse success() {
        return new BaseResponse<>("0000", "success", null);
    }
}