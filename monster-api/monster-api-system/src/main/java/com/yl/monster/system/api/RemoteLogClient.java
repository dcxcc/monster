package com.yl.monster.system.api;


import com.yl.common.core.constant.SecurityConstants;
import com.yl.common.core.web.page.PageResult;
import com.yl.monster.system.api.domain.LoginInformationLog;
import com.yl.monster.system.api.domain.OperLog;
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
@HttpExchange(url = "http://monster-modules-log")
public interface RemoteLogClient {

    @GetExchange("/log/page/{page}/{size}")
    PageResult page(@PathVariable Long page, @PathVariable Long size, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostExchange("/loginInformation")
    void saveLoginInformation(@RequestBody LoginInformationLog loginInformation, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostExchange("/log")
    void saveLog(@RequestBody OperLog operLog, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
