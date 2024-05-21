package com.yl.auth.domain.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * @Author: YL
 * @Date: 2024-05-20
 * @Project monster
 */
@Getter
@Setter
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}
