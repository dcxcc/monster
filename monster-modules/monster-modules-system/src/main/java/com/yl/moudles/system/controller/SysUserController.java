package com.yl.moudles.system.controller;

import com.yl.common.core.domain.R;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.core.web.controller.BaseController;
import com.yl.monster.system.api.domain.SysUser;
import com.yl.monster.system.api.model.LoginUser;
import com.yl.moudles.system.service.ISysPermissionService;
import com.yl.moudles.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {

    private final ISysUserService userService;
    private final ISysPermissionService permissionService;

    public SysUserController(ISysUserService userService, ISysPermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable String username) {
        SysUser sysUser = userService.getUserByName(username);
        if (StringUtils.isNull(sysUser)) {
            return R.fail("用户名或密码错误");
        }
        Set<String> roles = permissionService.getRolePermission(sysUser.getUserId());
        Set<String> permissions = permissionService.getMenuPermission(sysUser.getUserId());
        LoginUser loginUser = new LoginUser();
        loginUser.setSysUser(sysUser).setRoles(roles).setPermissions(permissions);
        return R.ok(loginUser);
    }
}
