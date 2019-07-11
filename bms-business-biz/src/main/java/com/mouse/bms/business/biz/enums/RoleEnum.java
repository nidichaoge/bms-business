package com.mouse.bms.business.biz.enums;

import lombok.Getter;

/**
 * CopyRight(C),mouse
 *
 * @author : mouse
 * @fileName : RoleEnum
 * @date : 2019/4/6 14:34
 * @description :
 */
@Getter
public enum RoleEnum {

    SYSTEM_ROLE((short) 0, "system", "系统管理员"),
    GENERAL_ROLE((short) 1, "general", "普通管理员");

    private final short value;
    private final String role;
    private final String description;

    RoleEnum(short value, String role, String description) {
        this.value = value;
        this.role = role;
        this.description = description;
    }

    public static RoleEnum sourceOf(int value) {
        for (RoleEnum name : RoleEnum.values()) {
            if (value == name.getValue()) {
                return name;
            }
        }
        return null;
    }

}
