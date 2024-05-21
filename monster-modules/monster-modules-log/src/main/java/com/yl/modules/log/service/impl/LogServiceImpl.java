package com.yl.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yl.monster.system.api.domain.OperLog;
import com.yl.modules.log.mapper.OperLogMapper;
import com.yl.modules.log.service.ILogService;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */
@Service
public class LogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements ILogService{
}
