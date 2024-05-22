package com.yl.moudles.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yl.common.core.constant.Constants;
import com.yl.common.core.text.Convert;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.redis.service.IRedisService;
import com.yl.moudles.system.domain.SysConfig;
import com.yl.moudles.system.mapper.SysConfigMapper;
import com.yl.moudles.system.service.ISysConfigService;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-22
 * @Project monster
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {
    private final IRedisService redisService;

    public SysConfigServiceImpl(IRedisService redisService) {
        this.redisService = redisService;
    }


    /**
     * 根据配置键获取配置值。
     * 首先尝试从缓存中获取配置值，如果缓存中不存在，则查询数据库，并将查询到的配置值更新到缓存中。
     *
     * @param configKey 配置项的键，用于查询配置值。
     * @return 配置项的值，如果找不到对应的配置键，则返回null。
     */
    @Override
    public String getConfigValuesByKey(String configKey) {
        // 尝试从缓存中获取配置值
        String configValues = Convert.toStr(redisService.get(getCacheKey(configKey)));
        if (StringUtils.isNotEmpty(configValues)){
            return configValues;
        }

        // 查询数据库中特定的配置项
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigKey, configKey);
        SysConfig sysConfig = getOne(wrapper);

        // 如果找到配置项，则更新缓存并返回配置值
        if (StringUtils.isNotNull(sysConfig)){
            redisService.set(getCacheKey(configKey),sysConfig.getConfigValue());
            return sysConfig.getConfigValue();
        }

        // 未找到配置项，返回null
        return null;
    }


    private String getCacheKey(String configKey) {
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
