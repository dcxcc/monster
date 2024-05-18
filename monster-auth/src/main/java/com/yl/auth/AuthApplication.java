package com.yl.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
