package com.yl.common.core.exception.request;

import com.yl.common.core.exception.base.BaseException;

import java.io.Serial;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
public class RequestAttributesNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    public RequestAttributesNotFoundException() {
        super("ServletRequestAttributes not found");
    }
}
