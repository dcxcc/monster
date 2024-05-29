package com.yl.common.security.interceptor;

import com.yl.common.core.constant.SecurityConstants;
import com.yl.common.core.context.SecurityContextHolder;
import com.yl.common.core.utils.ServletUtils;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.security.utils.auth.AuthUtils;
import com.yl.common.security.utils.SecurityUtils;
import com.yl.monster.system.api.model.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * @Author: YL
 * @Date: 2024-05-23
 * @Project monster
 */
public class HeaderInterceptor implements AsyncHandlerInterceptor {
    /**
     * 在处理请求之前进行拦截，用于设置用户安全上下文。
     *
     * @param request  HttpServletRequest对象，代表客户端的HTTP请求
     * @param response HttpServletResponse对象，用于向客户端发送HTTP响应
     * @param handler 将要处理请求的处理器对象
     * @return boolean值，如果返回true，则表示请求继续向下传递；如果返回false，则表示请求处理终止。
     * @throws Exception 抛出异常的情况主要为处理过程中的异常。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 当处理器不为HandlerMethod实例时，直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从请求头中提取用户ID、用户名和用户键，并设置到安全上下文中
        SecurityContextHolder.setUserId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID));
        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USERNAME));
        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));

        // 获取用户令牌并尝试登录验证
        String token = SecurityUtils.getToken();
        if (StringUtils.isNotEmpty(token)) {
            LoginUser loginUser = AuthUtils.getLoginUser(token);
            // 如果用户登录信息不为空，则验证登录用户是否过期，并设置登录用户上下文
            if (StringUtils.isNotNull(loginUser)) {
                AuthUtils.verifyLoginUserExpire(loginUser);
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
            }
        }

        // 继续处理请求
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.remove();
    }
}
