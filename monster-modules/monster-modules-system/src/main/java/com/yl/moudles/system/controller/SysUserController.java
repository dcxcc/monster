package com.yl.moudles.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yl.common.core.constant.UserConstants;
import com.yl.common.core.domain.R;
import com.yl.common.core.utils.StringUtils;
import com.yl.common.core.web.controller.BaseController;
import com.yl.common.core.web.page.PageResult;
import com.yl.common.security.annotation.InnerAuth;
import com.yl.monster.system.api.domain.SysUser;
import com.yl.monster.system.api.model.LoginUser;
import com.yl.moudles.system.service.ISysConfigService;
import com.yl.moudles.system.service.ISysPermissionService;
import com.yl.moudles.system.service.ISysUserService;
import org.springframework.web.bind.annotation.*;
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
    private final ISysConfigService configService;

    public SysUserController(ISysUserService userService, ISysPermissionService permissionService, ISysConfigService configService) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.configService = configService;
    }

    @GetMapping("/list/{page}/{size}")
    public PageResult list(SysUser sysUser, @PathVariable Long page, @PathVariable Long size) {
        Page<SysUser> sysUserPage = new Page<>(page, size);
        return getPageResult(userService.page(sysUserPage));
    }

    @InnerAuth
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

    @InnerAuth
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody SysUser sysUser) {
        String configValues = configService.getConfigValuesByKey("sys.account.registerUser");
        if (!Boolean.parseBoolean(configValues)) {
            return R.fail("当前系统没有开启注册功能！");
        }
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser.getUserName()))) {
            return R.fail("保存用户'" + sysUser.getUserName() + "'失败，注册账号已存在");
        }
        return R.ok(userService.save(sysUser));
    }
}
