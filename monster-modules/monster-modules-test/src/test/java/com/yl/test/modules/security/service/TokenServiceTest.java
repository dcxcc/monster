package com.yl.test.modules.security.service;

import com.alibaba.fastjson2.JSONObject;
import com.yl.common.core.utils.auth0.JwtAuthenticationService;
import com.yl.common.redis.service.IRedisService;
import com.yl.common.security.service.TokenService;
import com.yl.monster.system.api.domain.SysUser;
import com.yl.monster.system.api.model.LoginUser;
import com.yl.test.modules.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.yl.common.core.constant.CacheConstants.LOGIN_TOKEN_KEY;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
public class TokenServiceTest extends BaseTest {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IRedisService redisService;

    @Test
    public void test() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("create");
        SysUser sysUser = new SysUser();
        sysUser.setUserName("张三");
        sysUser.setUserId(Long.parseLong("333"));
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUser(sysUser);
        Map<String, Object> tokenMap = tokenService.createToken(loginUser);
        stopWatch.stop();
//        sleepSecond(1L);
        stopWatch.start("get");
        Object accessToken = tokenMap.get("access_token");
        String tokenString = accessToken.toString();
        log.info("access_token:{}  expires_in:{}", accessToken, tokenMap.get("expires_in"));
        String userKey = LOGIN_TOKEN_KEY + JwtAuthenticationService.getUserKey(tokenString);
        Object object = redisService.get(userKey);
        LoginUser user = JSONObject.parseObject(JSONObject.toJSONString(object), LoginUser.class);
        log.info("userInfo:{}", user);
        stopWatch.stop();
//        sleepSecond(1L);
        stopWatch.start("del");

        tokenService.delLoginUser(tokenString);

//        sleepSecond(3L);
        Object object1 = redisService.get(userKey);
        LoginUser user1 = JSONObject.parseObject(JSONObject.toJSONString(object), LoginUser.class);
        log.info("userInfo:{}", user1);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    public void test1() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("张三");
        sysUser.setUserId(Long.parseLong("333"));
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUser(sysUser);
        Map<String, Object> tokenMap = tokenService.createToken(loginUser);
    }
}
