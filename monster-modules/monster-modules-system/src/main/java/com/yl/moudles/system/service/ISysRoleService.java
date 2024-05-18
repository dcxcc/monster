package com.yl.moudles.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yl.monster.system.api.domain.SysRole;

import java.util.Set;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
public interface ISysRoleService extends IService<SysRole> {
    Set<String> selectRolePermissionByUserId(Long userId);
}
