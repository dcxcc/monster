package com.yl.moudles.system.domain;

import lombok.Data;

/**
 * @Author: YL
 * @Date: 2024-05-31
 * @Project monster
 */
@Data
public class SysUserPost {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;
}
