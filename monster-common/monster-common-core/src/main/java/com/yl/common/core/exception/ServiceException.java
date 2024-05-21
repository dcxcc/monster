package com.yl.common.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @Author: YL
 * @Date: 2024-05-20
 * @Project monster
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
