package com.yl.common.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.yl.common.core.constant.SecurityConstants;
import com.yl.common.core.text.Convert;
import com.yl.common.core.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 * @Description: 获取当前线程变量中的 用户id、用户名称、Token等信息
 * 注意： 必须在网关通过请求头的方法传入，同时在HeaderInterceptor拦截器设置值。 否则这里无法获取
 */
public class SecurityContextHolder {

/**
 * 定义一个静态常量THREAD_LOCAL，它是TransmittableThreadLocal类型的实例。
 * TransmittableThreadLocal是线程局部变量的扩展，不仅在当前线程中保持独立的副本，
 * 还允许在进行线程池任务迁移或子线程复制时传递这些副本，确保数据在不同线程间的安全隔离。
 * 此变量用于存储线程本地的Map对象，键为String类型，值为Object类型。
 */
private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = getLocalMap();
        map.put(key, value == null ? StringUtils.EMPTY : value);
    }

    public static String get(String key) {
        Map<String, Object> map = getLocalMap();
        return Convert.toStr(map.getOrDefault(key, StringUtils.EMPTY));
    }

    public static <T> T get(String key, Class<T> clazz) {
        Map<String, Object> map = getLocalMap();
        return StringUtils.cast(map.getOrDefault(key, null));
    }

    public static Long getUserId() {
        return Convert.toLong(get(SecurityConstants.DETAILS_USER_ID));
    }

    public static void setUserId(String account) {
        set(SecurityConstants.DETAILS_USER_ID, account);
    }

    public static String getUserName() {
        return get(SecurityConstants.DETAILS_USERNAME);
    }

    public static void setUserName(String username) {
        set(SecurityConstants.DETAILS_USERNAME, username);
    }

    public static String getUserKey() {
        return get(SecurityConstants.USER_KEY);
    }

    public static void setUserKey(String userKey) {
        set(SecurityConstants.USER_KEY, userKey);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static void setLocalMap(Map<String, Object> threadLocalMap) {
        THREAD_LOCAL.set(threadLocalMap);
    }

    /**
     * 获取一个线程本地的Map对象。如果当前线程中不存在该Map，则会新建一个并绑定到当前线程。
     *
     * @return 返回当前线程绑定的Map对象，用于存储线程本地数据。
     */
    private static Map<String, Object> getLocalMap() {
        // 从线程本地变量中获取当前线程的Map对象
        Map<String, Object> map = THREAD_LOCAL.get();
        // 如果当前线程尚未绑定Map，则创建一个新的ConcurrentHashMap并绑定到当前线程
        if (map == null) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

}
