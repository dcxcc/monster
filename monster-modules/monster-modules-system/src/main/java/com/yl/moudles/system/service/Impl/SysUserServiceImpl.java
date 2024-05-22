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

    /**
     * 检查用户名是否唯一
     * @param userName 要检查的用户名
     * @return 如果用户名唯一返回UserConstants.UNIQUE，如果用户名已存在返回UserConstants.NOT_UNIQUE
     */
    @Override
    public String checkUserNameUnique(String userName) {
        // 使用LambdaQueryWrapper创建查询条件，查询用户名为userName的记录数量
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserName, userName);

        // 根据查询条件查询记录数量
        long count = this.count(wrapper);

        // 如果查询结果大于0，表示用户名已存在，返回不唯一的标识
        if (count>0){
            return UserConstants.NOT_UNIQUE;
        }

        // 查询结果为0，表示用户名唯一，返回唯一的标识
        return UserConstants.UNIQUE;
    }
}
