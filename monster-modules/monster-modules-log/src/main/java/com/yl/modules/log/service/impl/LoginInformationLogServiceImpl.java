package com.yl.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yl.modules.log.mapper.LoginInformationLogMapper;
import com.yl.modules.log.service.ILoginInformationLogService;
import com.yl.monster.system.api.domain.LoginInformationLog;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-20
 * @Project monster
 */
@Service
public class LoginInformationLogServiceImpl extends ServiceImpl<LoginInformationLogMapper, LoginInformationLog> implements ILoginInformationLogService {
}
