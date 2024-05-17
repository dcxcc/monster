package com.yl.common.core.utils;

import com.alibaba.fastjson2.JSON;
import com.yl.common.core.domain.R;
import com.yl.common.core.exception.request.RequestAttributesNotFoundException;
import com.yl.common.core.text.Convert;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
public class ServletUtils {
    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(String name) {
        return Convert.toBool(getRequest().getParameter(name));
    }

    /**
     * 获取Boolean参数
     */
    public static Boolean getParameterToBool(String name, Boolean defaultValue) {
        return Convert.toBool(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        try {
            return getRequestAttributes().getRequest();
        } catch (Exception e) {
            throw new RequestAttributesNotFoundException();
        }
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        try {
            return getRequestAttributes().getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取当前线程绑定的Servlet请求属性。
     * 这个方法会从RequestContextHolder中获取当前请求的属性，如果属性是ServletRequestAttributes类型，则将其返回。
     * 如果当前线程没有绑定Servlet请求属性，或者绑定的属性不是ServletRequestAttributes类型，将抛出RequestAttributesNotFoundException异常。
     *
     * @return ServletRequestAttributes 当前线程的Servlet请求属性。
     * @throws RequestAttributesNotFoundException 如果当前线程没有找到合适的Servlet请求属性。
     */
    public static ServletRequestAttributes getRequestAttributes() {
        // 从RequestContextHolder中获取当前请求的属性
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        // 检查获取的属性是否为ServletRequestAttributes类型
        if (attributes instanceof ServletRequestAttributes) {
            return (ServletRequestAttributes) attributes;
        }
        // 如果不是ServletRequestAttributes类型，抛出异常
        throw new RequestAttributesNotFoundException();
    }

    /**
     * 从HTTP请求中获取指定请求头的值，并对其进行URL解码。
     *
     * @param request HttpServletRequest对象，代表客户端的HTTP请求。
     * @param name 请求头的名称。
     * @return 返回经过URL解码后的请求头值。如果指定的请求头不存在，则返回空字符串。
     */
    public static String getHeader(HttpServletRequest request, String name) {
        // 从请求中获取指定名称的请求头值
        String value = request.getHeader(name);
        // 如果请求头值为空或不存在，则返回空字符串
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        }
        // 对请求头值进行URL解码后返回
        return urlDecode(value);
    }

    /**
     * 从HttpServletRequest中获取所有头部信息。
     *
     * @param request HttpServletRequest对象，用于获取HTTP请求的头信息。
     * @return 一个Map对象，其中包含请求头的键值对。键和值都是字符串类型。
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        // 使用LinkedCaseInsensitiveMap来存储头信息，保证键的大小写不敏感，且保留插入顺序
        Map<String, String> map = new LinkedCaseInsensitiveMap<>();
        // 获取所有头部名称的枚举
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            // 遍历枚举，将头部名称和对应的值添加到map中
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            Logger logger = LoggerFactory.getLogger(ServletUtils.class);
            logger.error("An error occurred", e); // 使用error级别记录异常信息，同时附带堆栈跟踪

        }
    }

    /**
     * 判断是否为Ajax异步请求。
     * <p>该方法通过检查请求头中的信息和请求参数来判断是否为Ajax请求。</p>
     *
     * @param request HttpServletRequest对象，代表客户端的HTTP请求。
     * @return boolean 返回true如果请求被认为是Ajax请求，否则返回false。
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        // 检查请求头中是否接受JSON类型的数据
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            return true;
        }

        // 检查请求头中是否包含XMLHttpRequest标识
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
            return true;
        }

        // 检查请求URI是否以.json或.xml结尾
        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }

        // 检查请求参数中是否含有指定的Ajax标识
        String ajax = request.getParameter("__ajax");
        return StringUtils.inStringIgnoreCase(ajax, "json", "xml");
    }


    /**
     * 内容编码
     *
     * @param str 内容
     * @return 编码后的内容
     */
    public static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    /**
     * 内容解码
     *
     * @param str 内容
     * @return 解码后的内容
     */
    public static String urlDecode(String str) {
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param value    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value) {
        return webFluxResponseWriter(response, HttpStatus.OK, value, R.FAIL);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, Object value, int code) {
        return webFluxResponseWriter(response, HttpStatus.OK, value, code);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param status   http状态码
     * @param code     响应状态码
     * @param value    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, HttpStatus status, Object value, int code) {
        return webFluxResponseWriter(response, MediaType.APPLICATION_JSON_VALUE, status, value, code);
    }

    /**
     * 设置webflux模型响应
     *
     * @param response    ServerHttpResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param code        响应状态码
     * @param value       响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType, HttpStatus status, Object value, int code) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);
        R<?> result = R.fail(code, value.toString());
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
