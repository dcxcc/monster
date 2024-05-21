package com.yl.common.security.annotation;

import java.lang.annotation.*;

/**
 * @Author: YL
 * @Date: 2024-05-20
 * @Project monster
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InnerAuth {
    /**
     * 是否校验用户信息
     */
    boolean isUser() default false;
}
