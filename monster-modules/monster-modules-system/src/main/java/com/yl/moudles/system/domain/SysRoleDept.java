package com.yl.moudles.system.domain;


import lombok.Data;

/**
 * @Author: YL
 * @Date: 2024-05-31
 * @Project monster
 */

@Data
public class SysRoleDept {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;
}
