package com.yl.monster.system.api;


import com.yl.common.core.domain.R;
import com.yl.monster.system.api.model.LoginUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */
@HttpExchange(url = "http://monster-modules-system")
public interface RemoteUserClient {

    @GetExchange("/user/info/{username}")
    R<LoginUser> getUserInfo(@PathVariable String username);


}
