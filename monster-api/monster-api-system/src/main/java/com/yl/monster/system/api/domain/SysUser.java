package com.yl.monster.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yl.common.core.annotation.Excel;
import com.yl.common.core.annotation.Excels;
import com.yl.common.core.web.domain.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;
import java.util.List;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_user",excludeProperty = {"dept","roles","roleIds","postIds","roleId"})
public class SysUser extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    private Long deptId;

    /** 用户账号 */
    @Excel(name = "登录名称")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户名称")
    private String nickName;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别 */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

//    @TableLogic(value = "0",delval = "2")
    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登录IP */
    @Excel(name = "最后登录IP", type = Excel.Type.EXPORT)
    private String loginIp;

    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date loginDate;

    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    private SysDept dept;

    /** 角色对象 */
    private List<SysRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;

    /** 角色ID */
    private Long roleId;
}
