package com.yl.moudles.system.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yl.common.core.constant.UserConstants;
import com.yl.monster.system.api.domain.SysUser;
import com.yl.moudles.system.mapper.SysUserMapper;
import com.yl.moudles.system.service.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * @Author: YL
 * @Date: 2024-05-18
 * @Project monster
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getUserByName(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, username);
        return getOne(wrapper);
    }

    @Override
    public Boolean isAdmin(Long userId) {
        return UserConstants.ADMIN_ROLE_KEY.equals(baseMapper.isAdmin(userId));
    }
}
