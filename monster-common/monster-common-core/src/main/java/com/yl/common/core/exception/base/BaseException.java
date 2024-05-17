package com.yl.common.core.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

}
