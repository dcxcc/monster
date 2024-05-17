package com.yl.common.redis.service;



import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */

public interface IRedisService {
    /**
     * 将给定的键值对存储到某个数据结构中。
     *
     * @param key 用于标识存储项的键，不能为空。
     * @param value 要存储的值，可以是任意类型。
     */
    void set(String key, Object value);

    /**
     * 将给定的键值对存储起来，并设定一个过期时间。
     *
     * @param key 用于标识存储项的键，不能为空。
     * @param value 要存储的值，可以是任意类型，不能为空。
     * @param time 过期时间的长度。
     * @param timeUnit 过期时间的单位。
     */
    void set(String key, Object value, long time, TimeUnit timeUnit);


    /**
     * 根据键获取对应的对象。
     *
     * @param key 用于查找对象的键，不能为空。
     * @return 如果找到对应的键，则返回相应的对象；如果没有找到，则返回null。
     */
    Object get(String key);


    /**
     * 根据键删除对应的对象。
     *
     * @param key 用于删除对象的键，不能为空。
     * @return 如果删除成功，则返回true；否则返回false。
     */
    Boolean delete(String key);

    /**
     * 根据键删除对应的对象。
     *
     * @param keys 用于删除对象的键，不能为空。
     * @return 如果删除成功，则返回true；否则返回false。
     */
    Long delete(List<String> keys);

    /**
     * 设置键的过期时间。
     *
     * @param key 用于设置过期时间的键，不能为空。
     * @param time 过期时间，单位为秒。
     * @return 如果设置成功，则返回true；否则返回false。
     */
    Boolean expire(String key, long time);

    /**
     * 获取键的剩余过期时间。
     *
     * @param key 用于获取剩余过期时间的键，不能为空。
     * @return 剩余过期时间，单位为秒。如果键不存在或者已过期，则返回-1。
     */
    Long getExpire(String key);

    /**
     * 判断键是否存在。
     *
     * @param key 用于判断是否存在的键，不能为空。
     * @return 如果键存在，则返回true；否则返回false。
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 直接设置整个Hash结构
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * 删除Hash结构中的属性
     */
    void hDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     */
    Set<Object> sMembers(String key);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     */
    Boolean sIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     */
    Long sSize(String key);

    /**
     * 删除Set结构中的属性
     */
    Long sRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     */
    Long lSize(String key);

    /**
     * 根据索引获取List中的属性
     */
    Object lIndex(String key, long index);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, Object value);

    /**
     * 向List结构中添加属性
     */
    Long lPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * 从List结构中移除属性
     */
    Long lRemove(String key, long count, Object value);
}
