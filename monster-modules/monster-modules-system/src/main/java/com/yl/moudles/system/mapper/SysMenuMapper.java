package com.yl.moudles.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yl.moudles.system.domain.SysMenu;

import java.util.List;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> selectMenuPermsByUserId(Long userId);
}
