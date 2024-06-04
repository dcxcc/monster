package com.yl.moudles.system.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yl.common.core.utils.StringUtils;
import com.yl.monster.system.api.domain.SysRole;
import com.yl.moudles.system.mapper.SysRoleMapper;
import com.yl.moudles.system.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = this.baseMapper.selectRolePermissionByUserId(userId);
        HashSet<String> permsSet = new HashSet<>();
        perms.forEach(perm -> {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        });
        return permsSet;
    }
}
