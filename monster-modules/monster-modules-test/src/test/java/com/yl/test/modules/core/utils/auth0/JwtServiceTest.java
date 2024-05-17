package com.yl.test.modules.core.utils.auth0;

import com.yl.common.core.utils.auth0.JwtAuthenticationService;
import com.yl.test.modules.BaseTest;
import org.junit.Test;

import java.util.HashMap;

import static com.yl.common.core.constant.SecurityConstants.USER_KEY;

/**
 * @Author: YL
 * @Date: 2024-05-15
 * @Project monster
 */
public class JwtServiceTest extends BaseTest {
    @Test
    public void test() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", 1);
        String token = JwtAuthenticationService.generateToken(map, 10000L);
        sleepMillisSecond(1000L);
        boolean wait1sValidToken = JwtAuthenticationService.isValidToken(token);
        System.out.println("wait1sValidToken = " + wait1sValidToken);
        sleepMillisSecond(10000L);
        boolean wait11sValidToken = JwtAuthenticationService.isValidToken(token);
        System.out.println("wait11sValidToken = " + wait11sValidToken);
    }

    @Test
    public void test2() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", 1);
        map.put(USER_KEY, "123");
        String token = JwtAuthenticationService.generateToken(map, 1000000L);
        log.info("token:{}",token);
        sleepSecond(1L);
        String userKey = JwtAuthenticationService.getUserKey(token);
        log.info("userKey:{}",userKey);
    }

    @Test
    public void test3() {
        String userKey = JwtAuthenticationService.getUserKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InVzZXJfa2V5IjoiMTIzIiwidXNlcklkIjoxfSwiZXhwIjoxNzQ3NDc0NDM1LCJpYXQiOjE3MTU5Mzg0MzV9.IM6eK2LtzRir5Vf6Mkvlp1O-KEPX8jgRdRx4yLqoqtI");
        boolean validToken = JwtAuthenticationService.isValidToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InVzZXJfa2V5IjoiMTIzIiwidXNlcklkIjoxfSwiZXhwIjoxNzQ3NDc0NDM1LCJpYXQiOjE3MTU5Mzg0MzV9.IM6eK2LtzRir5Vf6Mkvlp1O-KEPX8jgRdRx4yLqoqtI");
        System.out.println("userKey = " + userKey);
        System.out.println("validToken = " + validToken);
    }
    @Test
    public void test4() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", 1);
        map.put(USER_KEY, "123");
        String token = JwtAuthenticationService.generateToken(map, 1000 * 60 * 60 * 24 * 365L);
        boolean validToken = JwtAuthenticationService.isValidToken(token);
        System.out.println("token = " + token);
        System.out.println("validToken = " + validToken);
    }
}
