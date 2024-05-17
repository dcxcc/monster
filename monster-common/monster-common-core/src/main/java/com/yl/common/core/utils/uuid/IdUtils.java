package com.yl.common.core.utils.uuid;

import java.util.UUID;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */
public class IdUtils {
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }
    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
