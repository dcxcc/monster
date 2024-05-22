package com.yl.moudles.system.domain;

import com.yl.common.core.annotation.Excel;
import com.yl.common.core.web.domain.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;

/**
 * @Author: YL
 * @Date: 2024-05-22
 * @Project monster
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class SysConfig extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 参数主键
     */
    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    private Long configId;

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空")
    @Size(max = 100, message = "参数名称不能超过100个字符")
    @Excel(name = "参数名称")
    private String configName;

    /**
     * 参数键名
     */
    @NotBlank(message = "参数键名长度不能为空")
    @Size(max = 100, message = "参数键名长度不能超过100个字符")
    @Excel(name = "参数键名")
    private String configKey;

    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空")
    @Size(max = 500, message = "参数键值长度不能超过500个字符")
    @Excel(name = "参数键值")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    private String configType;
}
