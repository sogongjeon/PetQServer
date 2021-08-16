package com.sogong.sogong.type.animal;

import com.sogong.sogong.type.EnumModel;

public enum ProtectType implements EnumModel {
    SHELTER("보호소"),
    FIND("발견"),
    PROTECT("개인 보호"),
    ;

    ProtectType(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
