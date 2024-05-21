package com.yl.common.core.exception;

import java.io.Serial;

/**
 * @Author: YL
 * @Date: 2024-05-21
 * @Project monster
 */
public class InnerAuthException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1L;

    public InnerAuthException(String message)
    {
        super(message);
    }
}
