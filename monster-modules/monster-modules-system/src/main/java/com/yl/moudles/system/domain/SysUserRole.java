package com.yl.moudles.system.domain;

import lombok.Data;

/**
 * @Author: YL
 * @Date: 2024-05-31
 * @Project monster
 */
@Data
public class SysUserRole {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
