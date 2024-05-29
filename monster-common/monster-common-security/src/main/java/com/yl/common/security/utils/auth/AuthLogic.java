package com.yl.common.security.utils.auth;

import com.yl.common.core.utils.SpringUtils;
import com.yl.common.security.service.TokenService;
import com.yl.monster.system.api.model.LoginUser;

/**
 * @Author: YL
 * @Date: 2024-05-29
 * @Project monster
 */
public class AuthLogic {
    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    public TokenService tokenService = SpringUtils.getBean(TokenService.class);

    public LoginUser getLoginUser(String token) {
        return tokenService.getLoginUser(token);
    }

    public void verifyLoginUserExpire(LoginUser loginUser) {
        tokenService.verifyToken(loginUser);
    }
}
