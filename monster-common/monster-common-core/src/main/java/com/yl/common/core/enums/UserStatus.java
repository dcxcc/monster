package com.yl.common.core.enums;


import lombok.Getter;

/**
 * @Author: YL
 * @Date: 2024-05-20
 * @Project monster
 */

@Getter
public enum UserStatus {
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

}
