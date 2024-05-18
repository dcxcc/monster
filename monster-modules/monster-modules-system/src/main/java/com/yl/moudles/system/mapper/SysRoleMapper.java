package com.yl.moudles.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yl.monster.system.api.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> selectRolePermissionByUserId(Long userId);
}
