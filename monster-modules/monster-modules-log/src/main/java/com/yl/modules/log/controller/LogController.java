package com.yl.modules.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.common.core.web.controller.BaseController;
import com.yl.common.core.web.page.PageResult;
import com.yl.modules.log.domain.OperLog;
import com.yl.modules.log.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */
@RestController()
@RequestMapping("/log")
public class LogController extends BaseController {
    private final ILogService logService;

    public LogController(ILogService logService) {
        this.logService = logService;
    }

    @GetMapping("/page/{page}/{size}")
    public PageResult page(@PathVariable Long page, @PathVariable Long size) {
        Page<OperLog> operLogPage = new Page<>(page,size);
        IPage<OperLog> pageData = logService.page(operLogPage);
        return getPageResult(pageData);
    }
}
