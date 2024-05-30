package com.yl.common.log.aspect;

import com.alibaba.fastjson2.JSON;
import com.yl.common.core.utils.ServletUtils;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.core.utils.ip.IpUtils;
import com.yl.common.log.annotation.Log;
import com.yl.common.log.enums.BusinessStatus;
import com.yl.common.log.service.AsyncLogService;
import com.yl.common.security.utils.SecurityUtils;
import com.yl.monster.system.api.domain.OperLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

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

    /**
     * 在方法返回后执行的切面逻辑。
     * 该方法会根据方法上的controllerLog注解，处理日志记录。
     *
     * @param joinPoint     切面连接点，代表被拦截的方法。
     * @param controllerLog controllerLog注解实例，用于判断是否需要记录日志。
     * @param jsonResult    被拦截方法的返回结果，通常为JSON格式的数据。
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        // 处理日志记录逻辑
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 当方法抛出异常时执行的后置处理。
     * 该方法会根据切面表达式确定的切入点，在抛出异常时自动调用，用于处理日志记录等后续工作。
     *
     * @param joinPoint     切入点，表示被拦截的方法。
     * @param controllerLog Log注解实例，表示被注解的方法。
     * @param e             抛出的异常对象。
     */
    @AfterThrowing(pointcut = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        // 处理日志，将异常信息记录下来
        handleLog(joinPoint, controllerLog, e, null);
    }

    /**
     * 处理日志记录，包括操作日志和异常日志。
     *
     * @param joinPoint     AOP切面的连接点，用于获取目标方法信息。
     * @param controllerLog 控制器日志对象，暂未使用。
     * @param e             异常对象，如果方法正常执行则为null。
     * @param jsonResult    方法返回的JSON结果，暂未使用。
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
                    .setOperTime(new Date())
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
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 异步保存操作日志
//            asyncLogService.asyncTask();
            asyncLogService.saveLog(operLog);
        } catch (Exception exp) {
            // 记录处理日志时发生的异常
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
        }
    }

    /**
     * 获取控制器方法的描述信息，并填充到操作日志对象中。
     * @param joinPoint 切面编程的连接点，用于获取方法信息。
     * @param log 记录请求日志的对象，包含是否保存请求和响应数据的标志。
     * @param operLog 操作日志对象，用于记录具体的操作信息。
     * @param jsonResult 方法的返回结果，转化为JSON字符串后保存。
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperLog operLog, Object jsonResult) {
        // 如果设置为保存请求数据，则将请求信息保存到操作日志中
        if (log.isSaveRequestData()) {
            setRequestData(joinPoint, operLog);
        }
        // 如果设置为保存响应数据且响应结果不为空，则将处理后的响应结果保存到操作日志中
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult)){
            operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }
        // 设置操作日志的业务类型、标题和操作者类型
        operLog.setBusinessType(log.businessType().ordinal())
                .setTitle(log.title())
                .setOperatorType(log.operatorType().ordinal());
    }


    private void setRequestData(JoinPoint joinPoint, OperLog operLog) {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            operLog.setOperParam(StringUtils.substring(ServletUtils.getRequest().getQueryString(), 0, 2000));
        }
    }

    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null) {
            for (Object o : paramsArray) {
                if (StringUtils.isNotNull(o) && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params.append(jsonObj.toString()).append(" ");
                    } catch (Exception ignored) {

                    }
                }
            }
        }
        return params.toString().trim();
    }

/**
 * 检查给定对象是否为过滤条件中指定的类型。
 * 该方法支持检查null值、数组、Collection、Map以及特定的类实例。
 * 特定的类实例包括：MultipartFile、HttpServletRequest、HttpServletResponse和BindingResult。
 *
 * @param o 要检查的对象。
 * @return 如果对象是可接受的类型，则返回true；否则返回false。
 */
private boolean isFilterObject(final Object o) {
    if (o == null) {
        // 处理空值情况
        return false;
    }
    Class<?> clazz = o.getClass();
    // 检查是否为数组，并且数组元素类型是否为MultipartFile
    if (clazz.isArray()) {
        return MultipartFile.class.isAssignableFrom(clazz.getComponentType());
    }
    // 检查是否为Collection类型，并且集合中至少有一个元素是MultipartFile类型
    if (Collection.class.isAssignableFrom(clazz)) {
        Collection<?> collection = (Collection<?>) o;
        return collection.stream().anyMatch(MultipartFile.class::isInstance);
    }
    // 检查是否为Map类型，并且Map的值中至少有一个是MultipartFile类型
    if (Map.class.isAssignableFrom(clazz)) {
        Map<?, ?> map = (Map<?, ?>) o;
        return map.values().stream().anyMatch(MultipartFile.class::isInstance);
    }
    // 检查对象是否为指定的实例之一
    return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
            || o instanceof BindingResult;
}


}
