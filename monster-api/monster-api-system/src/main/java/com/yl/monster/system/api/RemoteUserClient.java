package com.yl.monster.system.api;


import com.yl.common.core.constant.SecurityConstants;
import com.yl.common.core.domain.R;
import com.yl.monster.system.api.domain.SysUser;
import com.yl.monster.system.api.model.LoginUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */
@HttpExchange(url = "http://monster-modules-system")
public interface RemoteUserClient {

    @GetExchange("/user/info/{username}")
    R<LoginUser> getUserInfo(@PathVariable String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostExchange("user/register")
    R<?> registerUserInfo(@RequestBody SysUser sysUser,@RequestHeader(SecurityConstants.FROM_SOURCE) String inner);
}
