package com.yl.gateway.filter;


import com.yl.common.core.constant.CacheConstants;
import com.yl.common.core.constant.HttpStatus;
import com.yl.common.core.constant.SecurityConstants;
import com.yl.common.core.constant.TokenConstants;
import com.yl.common.core.utils.ServletUtils;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.core.utils.auth0.JwtAuthenticationService;
import com.yl.common.redis.service.IRedisService;
import com.yl.gateway.config.properties.IgnoreWhiteProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: YL
 * @Date: 2024-05-17
 * @Project monster
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    private final IgnoreWhiteProperties ignoreWhiteProperties;
    final IRedisService redisService;

    public AuthFilter(IgnoreWhiteProperties ignoreWhiteProperties, IRedisService redisService) {
        this.ignoreWhiteProperties = ignoreWhiteProperties;
        this.redisService = redisService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();
        String url = request.getURI().getPath();
        String token;
        if (StringUtils.matches(url, ignoreWhiteProperties.getWhites())) {
            return chain.filter(exchange);
        }
        if (ignoreWhiteProperties.getWs().equals(url)) {
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            token = queryParams.get(TokenConstants.AUTHENTICATION).getFirst();
        } else {
            token = getToken(request);
        }
        if (StringUtils.isEmpty(token)) {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        if (!JwtAuthenticationService.isValidToken(token)) {
            return unauthorizedResponse(exchange, "令牌已过期或验证不正确");
        }
        String userKey = JwtAuthenticationService.getUserKey(token);
        if (!redisService.hasKey(getTokenKey(userKey))) {
            return unauthorizedResponse(exchange, "登录状态已过期");
        }
        String userId = JwtAuthenticationService.getUserId(token);
        String userName = JwtAuthenticationService.getUserName(token);
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userName)) {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }
        addHeader(mutate, SecurityConstants.USER_KEY, userKey);
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userId);
        addHeader(mutate, SecurityConstants.DETAILS_USERNAME, userName);

        removeHeader(mutate);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void removeHeader(ServerHttpRequest.Builder mutate) {
        mutate.headers(httpHeaders -> httpHeaders.remove(SecurityConstants.FROM_SOURCE)).build();
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg) {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }

    private String getTokenKey(String token) {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(TokenConstants.AUTHENTICATION);
        if (StringUtils.isNotEmpty(token) && token != null) {
            if (token.startsWith(TokenConstants.PREFIX)) {
                token = token.replaceFirst(TokenConstants.PREFIX, StringUtils.EMPTY);
            }
        }
        return token;
    }

    @Override
    public int getOrder() {
        return -200;
    }
}
