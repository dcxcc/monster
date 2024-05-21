package com.yl.moudles.system;

import com.yl.common.security.annotation.EnableCustomConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@MapperScan("com.yl.**.mapper")
@SpringBootApplication
@EnableCustomConfig
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
