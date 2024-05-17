package com.yl.common.core.utils.auth0;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yl.common.core.constant.SecurityConstants;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
public class JwtAuthenticationService {
    //todo nacos存放
    public static final String CLAIMS_NAME = "data";
    // 用于签名JWT的秘钥 // 请替换为你自己的秘钥
    private static final String SECRET_KEY = "monster-secret-key";

    public static String generateToken(Map<String, Object> claimsMap, long expirationMillis) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMillis);
        return JWT.create()
                .withClaim(CLAIMS_NAME, claimsMap)
                .withExpiresAt(expirationDate)
                .withIssuedAt(now)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }


    public static boolean isValidToken(String token) {
        try {
            // 检查是否过期
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            // 解析或验证失败
            return false;
        }
    }


    public static String getUserKey(String token) {
        DecodedJWT jwt = validateToken(token);
        return getValue(jwt, SecurityConstants.USER_KEY);
    }

    public static String getUserId(String token) {
        DecodedJWT jwt = validateToken(token);
        return getValue(jwt, SecurityConstants.DETAILS_USER_ID);
    }

    public static String getUserName(String token) {
        DecodedJWT jwt = validateToken(token);
        return getValue(jwt, SecurityConstants.DETAILS_USERNAME);
    }

    public static String getUserKey(DecodedJWT jwt) {
        return getValue(jwt, SecurityConstants.USER_KEY);
    }

    public static String getUserName(DecodedJWT jwt) {
        return getValue(jwt, SecurityConstants.DETAILS_USERNAME);
    }

    public static String getUserId(DecodedJWT jwt) {
        return getValue(jwt, SecurityConstants.DETAILS_USER_ID);
    }

    private static String getValue(DecodedJWT jwt, String userKey) {
        return jwt.getClaim(CLAIMS_NAME).asMap().get(userKey).toString();
    }

    private static DecodedJWT validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            throw new RuntimeException("token验证失败");
        }
    }
}
