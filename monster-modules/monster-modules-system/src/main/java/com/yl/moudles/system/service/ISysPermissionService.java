package com.yl.moudles.system.service;

import java.util.Set;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
public interface ISysPermissionService {
    /**
     * 获取角色数据权限
     *
     * @param userId 用户Id
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(Long userId);

    /**
     * 获取菜单数据权限
     *
     * @param userId 用户Id
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(Long userId);
}
