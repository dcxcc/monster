package com.yl.common.log.aspect;

import com.yl.common.core.utils.ServletUtils;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.core.utils.ip.IpUtils;
import com.yl.common.log.annotation.Log;
import com.yl.common.log.enums.BusinessStatus;
import com.yl.common.log.service.AsyncLogService;
import com.yl.common.security.utils.SecurityUtils;
import com.yl.monster.system.api.domain.OperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: YL
 * @Date: 2024-05-23
 * @Project monster
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    private final AsyncLogService asyncLogService;

    public LogAspect(AsyncLogService asyncLogService) {
        this.asyncLogService = asyncLogService;
    }

    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 处理日志记录，包括操作日志和异常日志。
     *
     * @param joinPoint AOP切面的连接点，用于获取目标方法信息。
     * @param controllerLog 控制器日志对象，暂未使用。
     * @param e 异常对象，如果方法正常执行则为null。
     * @param jsonResult 方法返回的JSON结果，暂未使用。
     */
    private void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            // 初始化操作日志对象
            OperLog operLog = new OperLog();
            // 获取请求IP地址
            String ipAddr = IpUtils.getIpAddr(ServletUtils.getRequest());
            // 获取当前登录用户名
            String username = SecurityUtils.getUsername();
            // 获取目标类名和方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            // 填充操作日志基本信息
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal())
                    .setOperIp(ipAddr)
                    .setOperUrl(ServletUtils.getRequest().getRequestURI())
                    .setMethod(className + "." + methodName + "()")
                    .setRequestMethod(ServletUtils.getRequest().getMethod())
            ;
            // 如果用户名不为空，则设置操作人名称
            if (StringUtils.isNotBlank(username)) {
                operLog.setOperName(username);
            }
            // 如果存在异常，设置操作状态为失败，并记录异常信息
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal())
                        .setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            // 异步保存操作日志
            asyncLogService.saveLog(operLog);
        }
        catch (Exception exp){
            // 记录处理日志时发生的异常
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
        }
    }

}
