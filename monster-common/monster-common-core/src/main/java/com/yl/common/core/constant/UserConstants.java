package com.yl.common.core.constant;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
public class UserConstants {
    public static final String ADMIN_ROLE_KEY = "admin";
    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;

    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;
    /**
     * 校验返回结果码
     */
    public static final String UNIQUE = "0";

    public static final String NOT_UNIQUE = "1";
}
