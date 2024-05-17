package com.yl.monster.system.api.factory;

import com.yl.monster.system.api.RemoteLogClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */
@Configuration(proxyBeanMethods = false)
public class RemoteLogFactory {
    /**
     * 创建并配置一个RemoteLogClient实例，该实例使用WebClient进行HTTP通信。
     *
     * @param function 一个ReactorLoadBalancerExchangeFilterFunction实例，用于负载均衡和请求处理。
     * @return RemoteLogClient 返回一个配置好的RemoteLogClient实例，可用于与远程日志服务进行交互。
     */
    @Bean
    public RemoteLogClient remoteLogClient(ReactorLoadBalancerExchangeFilterFunction function) {
        // 使用给定的函数构建WebClient，并配置到WebClientAdapter中
        WebClient webClient = WebClient.builder().filter(function).build();
        // 通过HttpServiceProxyFactory构建并配置代理，然后创建RemoteLogClient实例
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build().createClient(RemoteLogClient.class);
    }

}
