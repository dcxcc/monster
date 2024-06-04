package com.yl.moudles.system.domain;

import com.yl.common.core.annotation.Excel;
import com.yl.common.core.web.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @Author: YL
 * @Date: 2024-05-31
 * @Project monster
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserLevel extends TreeEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 等级名称
     */
    @Excel(name = "等级名称")
    private String levelName;

}
