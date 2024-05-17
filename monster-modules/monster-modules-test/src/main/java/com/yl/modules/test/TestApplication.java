package com.yl.modules.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class,args);
    }
}
