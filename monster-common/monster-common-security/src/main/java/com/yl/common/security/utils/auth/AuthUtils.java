package com.yl.common.security.utils.auth;

import com.yl.monster.system.api.model.LoginUser;

/**
 * @Author: YL
 * @Date: 2024-05-29
 * @Project monster
 */
public class AuthUtils {
    /**
     * 底层的 AuthLogic 对象
     */
    public static AuthLogic authLogic = new AuthLogic();

    public static LoginUser getLoginUser(String token) {
        return authLogic.getLoginUser(token);
    }

    public static void verifyLoginUserExpire(LoginUser loginUser) {
        authLogic.verifyLoginUserExpire(loginUser);
    }
}
