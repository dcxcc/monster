package com.yl.modules.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.common.core.web.controller.BaseController;
import com.yl.common.core.web.domain.AjaxResult;
import com.yl.common.core.web.page.PageResult;
import com.yl.common.security.annotation.InnerAuth;
import com.yl.monster.system.api.domain.OperLog;
import com.yl.modules.log.service.ILogService;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */
@RestController()
@RequestMapping("/log")
public class OperLogController extends BaseController {
    private final ILogService logService;

    public OperLogController(ILogService logService) {
        this.logService = logService;
    }

    @InnerAuth
    @PostMapping
    public AjaxResult save(@RequestBody OperLog operLog) {
        return toAjax(logService.save(operLog));
    }

    @GetMapping("/page/{page}/{size}")
    public PageResult page(@PathVariable Long page, @PathVariable Long size) {
        Page<OperLog> operLogPage = new Page<>(page, size);
        IPage<OperLog> pageData = logService.page(operLogPage);
        return getPageResult(pageData);
    }
}
