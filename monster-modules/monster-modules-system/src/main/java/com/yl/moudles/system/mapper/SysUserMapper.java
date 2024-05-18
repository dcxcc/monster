package com.yl.moudles.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yl.monster.system.api.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    String isAdmin(Long userId);
}
