package com.yl.modules.log;

import com.yl.common.security.annotation.EnableCustomConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */
@SpringBootApplication
@EnableCustomConfig
public class LogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class,args);
    }
}
