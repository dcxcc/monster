package com.yl.moudles.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.yl.**.mapper")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
