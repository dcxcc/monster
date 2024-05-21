package com.yl.modules.log.controller;

import com.yl.common.core.web.controller.BaseController;
import com.yl.common.core.web.domain.AjaxResult;
import com.yl.common.security.annotation.InnerAuth;
import com.yl.modules.log.service.ILoginInformationLogService;
import com.yl.monster.system.api.domain.LoginInformationLog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: YL
 * @Date: 2024-05-20
 * @Project monster
 */
@RestController
@RequestMapping("/loginInformation")
public class LoginInformationLogController extends BaseController {
    private final ILoginInformationLogService informationLogService;

    public LoginInformationLogController(ILoginInformationLogService informationLogService) {
        this.informationLogService = informationLogService;
    }

    @InnerAuth
    @PostMapping
    public AjaxResult add(@RequestBody LoginInformationLog informationLog)
    {
        return toAjax(informationLogService.save(informationLog));
    }
}
