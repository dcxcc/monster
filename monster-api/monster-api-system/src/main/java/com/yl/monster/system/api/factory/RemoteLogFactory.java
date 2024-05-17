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
    @Bean
    public RemoteLogClient remoteLogClient(ReactorLoadBalancerExchangeFilterFunction function) {
        WebClient webClient = WebClient.builder().filter(function).build();
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build().createClient(RemoteLogClient.class);

    }
}
