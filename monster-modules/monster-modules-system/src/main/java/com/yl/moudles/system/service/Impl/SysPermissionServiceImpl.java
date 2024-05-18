package com.yl.moudles.system.service.Impl;

import com.yl.common.core.constant.UserConstants;
import com.yl.moudles.system.service.ISysMenuService;
import com.yl.moudles.system.service.ISysPermissionService;
import com.yl.moudles.system.service.ISysRoleService;
import com.yl.moudles.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {
    private final ISysUserService userService;
    private final ISysRoleService roleService;
    private final ISysMenuService menuService;

    public SysPermissionServiceImpl(ISysUserService userService, ISysRoleService roleService, ISysMenuService menuService) {
        this.userService = userService;
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @Override
    public Set<String> getRolePermission(Long userId) {
        Set<String> roles = new HashSet<String>();
        if (userService.isAdmin(userId)) {
            roles.add(UserConstants.ADMIN_ROLE_KEY);
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(userId));
        }
        return roles;
    }

    @Override
    public Set<String> getMenuPermission(Long userId) {
        HashSet<String> perms = new HashSet<>();
        if (userService.isAdmin(userId)) {
            perms.add("*:*:*");
        } else {
            perms.addAll(menuService.selectMenuPermsByUserId(userId));
        }
        return perms;
    }
}
