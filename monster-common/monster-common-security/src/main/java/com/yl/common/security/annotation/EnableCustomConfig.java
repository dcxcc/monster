package com.yl.common.security.annotation;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author: YL
 * @Date: 2024-05-21
 * @Project monster
 */
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 开启线程异步执行
@EnableAsync
@EnableDiscoveryClient
public @interface EnableCustomConfig {
}
