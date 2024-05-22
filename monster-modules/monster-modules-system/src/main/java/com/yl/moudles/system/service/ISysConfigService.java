package com.yl.moudles.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yl.moudles.system.domain.SysConfig;

/**
 * @Author: YL
 * @Date: 2024-05-22
 * @Project monster
 */
public interface ISysConfigService extends IService<SysConfig> {
    String getConfigValuesByKey(String configKey);
}
