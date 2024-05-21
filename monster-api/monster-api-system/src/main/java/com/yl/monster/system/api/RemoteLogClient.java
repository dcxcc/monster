package com.yl.monster.system.api;


import com.yl.common.core.domain.R;
import com.yl.common.core.web.page.PageResult;
import com.yl.monster.system.api.domain.LoginInformationLog;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    PageResult page(@PathVariable Long page, @PathVariable Long size);

    @PostExchange("/loginInformation")
    R<Boolean> saveLoginInformation(@RequestBody LoginInformationLog loginInformation);

}
