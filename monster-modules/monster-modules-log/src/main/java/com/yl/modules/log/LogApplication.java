package com.yl.modules.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class,args);
    }
}
