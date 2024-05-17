package com.yl.test.modules.redis;

import com.yl.common.redis.service.IRedisService;
import com.yl.test.modules.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.TimeUnit;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
public class RedisServiceTest extends BaseTest {
    @Autowired
    private IRedisService redisService;
    @Test
    public void set() {
        redisService.set("testExpire","100s",100L, TimeUnit.SECONDS);
    }
    @Test
    public void getRedisTime(){
        System.out.println("Expire = " + redisService.hasKey("login_tokens:9ca5f583-071d-441f-82f2-33e3be93a4ae"));
    }

}
