package com.yl.common.security.service;

import com.alibaba.fastjson2.JSONObject;
import com.yl.common.core.constant.CacheConstants;
import com.yl.common.core.constant.SecurityConstants;
import com.yl.common.core.utils.ServletUtils;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.core.utils.auth0.JwtAuthenticationService;
import com.yl.common.core.utils.ip.IpUtils;
import com.yl.common.core.utils.uuid.IdUtils;
import com.yl.common.redis.service.IRedisService;
import com.yl.common.security.utils.SecurityUtils;
import com.yl.monster.system.api.model.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */
@Component
public class TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenService.class);
    private final IRedisService redisService;

    public TokenService(IRedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 定义1秒的毫秒数。
     */
    protected static final long MILLIS_SECOND = 1000;

    /**
     * 定义1分钟的毫秒数。
     */
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    /**
     * 缓存过期时间，单位为毫秒。过期时间基于CacheConstants中的EXPIRATION常量和定义的分钟单位进行计算。
     */
    private final static long EXPIRE_TIME = CacheConstants.EXPIRATION * MILLIS_MINUTE;

    /**
     * 访问令牌的键，使用CacheConstants中定义的LOGIN_TOKEN_KEY。
     */
    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    /**
     * 刷新令牌的时间间隔，单位为毫秒。时间间隔基于CacheConstants中的REFRESH_TIME常量和定义的分钟单位进行计算。
     */
    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    /**
     * 创建用户登录令牌
     *
     * @param loginUser 登录用户信息，包含系统用户详细信息
     * @return 包含访问令牌和过期时间的Map
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        // 生成唯一的令牌
        String token = IdUtils.randomUUID();
        // 获取用户ID和用户名
        Long userId = loginUser.getSysUser().getUserId();
        String userName = loginUser.getSysUser().getUserName();
        // 设置登录用户的令牌、用户ID、用户名和IP地址
        loginUser.setToken(token)
                .setUserid(userId)
                .setUsername(userName)
                .setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        // 刷新用户令牌
        refreshToken(loginUser);
        // 准备令牌声明Map
        HashMap<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);
        // 准备响应Map，包含JWT令牌和过期时间
        HashMap<String, Object> rspMap = new HashMap<>();
        // 生成JWT令牌
        String jwt = JwtAuthenticationService.generateToken(claimsMap, EXPIRE_TIME);
        rspMap.put("access_token", jwt);
        rspMap.put("expires_in", EXPIRE_TIME);
        return rspMap;
    }

    public LoginUser getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 根据token获取登录用户信息。
     *
     * @param token 用户的令牌，用于识别和验证用户。
     * @return LoginUser 如果找到对应的用户信息，则返回LoginUser对象；否则返回null。
     */
    public LoginUser getLoginUser(String token) {

        LoginUser user = null;

        if (StringUtils.isEmpty(token)) {
            log.warn("Token is empty.");
            return null;
            // 提前返回，避免不必要的操作
        }

        try {
            // 验证token的有效性（假设JwtAuthenticationService中存在该方法）
            if (!JwtAuthenticationService.isValidToken(token)) {
                log.warn("Invalid token.");
                return null;
            }

            // 从token获取用户key
            String userKey = JwtAuthenticationService.getUserKey(token);
            user = getUserFromRedis(userKey);

        } catch (Exception e) {
            log.error("Failed to get user information.", e);
            // 记录详细的错误日志
            // 这里不再抛出运行时异常，因为异常类型和原因已经在日志中记录，业务逻辑可以根据需要决定是否需要再次处理异常
        }

        return user;
    }

    /**
     * 从Redis获取用户信息。
     *
     * @param userKey 用户的键
     * @return LoginUser 对象，如果找不到则为null。
     */
    private LoginUser getUserFromRedis(String userKey) {
        Object object = redisService.get(userKey);
        if (object != null) {
            // 这里假设redis存储的已经是LoginUser对象的JSON字符串
            return JSONObject.parseObject(JSONObject.toJSONString(object), LoginUser.class);
        }
        return null;
        // 如果Redis中没有找到用户信息，则返回null
    }


    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = JwtAuthenticationService.getUserKey(token);
            redisService.delete(getTokenKey(userKey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }
    /**
     * 刷新用户登录令牌。
     * 该方法会更新用户的登录时间（loginTime）和过期时间（expireTime），并重新在Redis中设置对应的令牌（token），
     * 以确保用户会话的有效性。
     *
     * @param loginUser 登录用户对象，包含用户的登录信息和令牌。
     */
    private void refreshToken(LoginUser loginUser) {
        // 更新用户的登录时间和过期时间
        loginUser.setLoginTime(System.currentTimeMillis())
                .setExpireTime(loginUser.getLoginTime() + EXPIRE_TIME);
        // 生成令牌的键值
        String tokenKey = getTokenKey(loginUser.getToken());
        // 在Redis中设置用户信息，确保会话在指定时间内有效
        redisService.set(tokenKey, loginUser, EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }

}
