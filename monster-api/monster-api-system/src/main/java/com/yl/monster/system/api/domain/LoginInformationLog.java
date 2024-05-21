package com.yl.monster.system.api.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yl.common.core.annotation.Excel;
import com.yl.common.core.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

/**
 * @Author: YL
 * @Date: 2024-05-20
 * @Project monster
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "login_information_log",excludeProperty = {"createBy","createTime","updateBy","updateTime","remark"})
public class LoginInformationLog extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long infoId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String userName;

    /**
     * 状态 0成功 1失败
     */
    @Excel(name = "状态", readConverterExp = "0=成功,1=失败")
    private String status;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String ipaddr;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String msg;
    /**
     * 访问时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date accessTime;
}
