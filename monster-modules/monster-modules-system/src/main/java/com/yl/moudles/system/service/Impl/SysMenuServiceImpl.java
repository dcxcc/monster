package com.yl.moudles.system.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yl.common.core.utils.StringUtils;
import com.yl.moudles.system.domain.SysMenu;
import com.yl.moudles.system.mapper.SysMenuMapper;
import com.yl.moudles.system.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = this.baseMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        perms.forEach(perm -> {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        });
        return permsSet;
    }
}
