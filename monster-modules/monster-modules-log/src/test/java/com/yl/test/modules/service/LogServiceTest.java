package com.yl.test.modules.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.modules.log.domain.OperLog;
import com.yl.modules.log.mapper.LogMapper;
import com.yl.modules.log.service.ILogService;
import com.yl.test.modules.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */
public class LogServiceTest extends BaseTest {
    @Autowired
    LogMapper logMapper;
    @Autowired
    ILogService logService;
    @Test
    public void testMapper() {
        List<OperLog> operLogs = logMapper.selectList(null);
        operLogs.forEach(System.out::println);
    }
    @Test
    public void testService() {
        List<OperLog> operLogs = logService.list();
        operLogs.forEach(System.out::println);
    }
    @Test
    public void testPage(){
        Page<OperLog> operLogPage = new Page<>(2,3);
        Page<OperLog> page = logService.page(operLogPage);
        page.getRecords().forEach(System.out::println);
        System.out.println("下一页 " +page.hasNext());
        System.out.println("上一页 " +page.hasPrevious());
        System.out.println("总记录数 " +page.getTotal());
    }
}
