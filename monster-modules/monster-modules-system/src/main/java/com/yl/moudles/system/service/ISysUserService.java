package com.yl.moudles.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yl.monster.system.api.domain.SysUser;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
public interface ISysUserService extends IService<SysUser> {
    SysUser getUserByName(String username);

    Boolean isAdmin(Long userId);

    String checkUserNameUnique(String userName);
}
