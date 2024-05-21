package com.yl.modules.test.controller;

import com.yl.common.core.constant.SecurityConstants;
import com.yl.common.core.web.controller.BaseController;
import com.yl.common.core.web.domain.AjaxResult;
import com.yl.common.core.web.page.PageResult;
import com.yl.common.redis.service.IRedisService;

import com.yl.common.security.service.TokenService;
import com.yl.monster.system.api.RemoteLogClient;
import com.yl.monster.system.api.domain.SysUser;
import com.yl.monster.system.api.model.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: YL
 * @Date: 2024-05-14
 * @Project monster
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {
    private final TokenService tokenService;
    final
    IRedisService redisService;

    final
    RemoteLogClient remoteLogClient;

    public TestController(IRedisService redisService, RemoteLogClient remoteLogClient, TokenService tokenService) {
        this.redisService = redisService;
        this.remoteLogClient = remoteLogClient;
        this.tokenService = tokenService;
    }

    @GetMapping("/get")
    public String test() {
        redisService.set("test", "test");
        System.out.println("redisService = " + redisService);
        return "redisService";
    }

    @GetMapping("page/{page}/{size}")
    public PageResult page(@PathVariable Long page, @PathVariable Long size) {
        //        Mono<PageResult> pageResultMono = webClient.get().uri("http://monster-modules-log/log/page").retrieve().bodyToMono(PageResult.class);
//        PageResult pageResult = pageResultMono.block();
        return remoteLogClient.page(page, size, SecurityConstants.INNER);
    }

    @GetMapping("/token")
    public AjaxResult getToken(){
        SysUser sysUser = new SysUser();
        sysUser.setUserName("张三");
        sysUser.setUserId(Long.parseLong("333"));
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUser(sysUser);
        Map<String, Object> tokenMap = tokenService.createToken(loginUser);
        return AjaxResult.success(tokenMap);
    }

}
