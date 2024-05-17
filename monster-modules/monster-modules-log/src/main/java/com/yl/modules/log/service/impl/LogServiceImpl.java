package com.yl.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yl.modules.log.domain.OperLog;
import com.yl.modules.log.mapper.LogMapper;
import com.yl.modules.log.service.ILogService;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, OperLog> implements ILogService{
}
