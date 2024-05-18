package com.yl.moudles.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yl.moudles.system.domain.SysMenu;

import java.util.Collection;
import java.util.Set;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
public interface ISysMenuService extends IService<SysMenu> {
    Set<String> selectMenuPermsByUserId(Long userId);
}
