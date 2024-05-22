package com.yl.modules.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.common.core.web.controller.BaseController;
import com.yl.common.core.web.domain.AjaxResult;
import com.yl.common.core.web.page.PageResult;
import com.yl.common.security.annotation.InnerAuth;
import com.yl.modules.log.service.ILoginInformationLogService;
import com.yl.monster.system.api.domain.LoginInformationLog;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("page")
    public PageResult page(@RequestParam(value = "page", defaultValue = "1") Long page,
                           @RequestParam(value = "size", defaultValue = "10") Long size)
    {
        Page<LoginInformationLog> operLogPage = new Page<>(page,size);
        return getPageResult(informationLogService.page(operLogPage));
    }
}
