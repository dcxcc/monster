package com.yl.auth.controller;

import com.yl.auth.domain.dto.LoginBody;
import com.yl.auth.domain.dto.RegisterBody;
import com.yl.auth.service.LoginService;
import com.yl.common.core.domain.R;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.core.utils.auth0.JwtAuthenticationService;
import com.yl.common.security.service.TokenService;
import com.yl.common.security.utils.SecurityUtils;
import com.yl.monster.system.api.model.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: YL
 * @Date: 2024-05-20
 * @Project monster
 */
@RestController
public class TokenController {
    private final TokenService tokenService;
    private final LoginService loginService;

    public TokenController(TokenService tokenService, LoginService loginService) {
        this.tokenService = tokenService;
        this.loginService = loginService;
    }

    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = loginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String userName = JwtAuthenticationService.getUserName(token);
            tokenService.delLoginUser(token);
            loginService.logout(userName);
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody) {
        loginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }


}
