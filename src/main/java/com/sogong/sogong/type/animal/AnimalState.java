package com.sogong.sogong.type.animal;

import com.sogong.sogong.type.EnumModel;

public enum AnimalState implements EnumModel {
    NOTICE("공지사항"),
    EVENT("이벤트"),
    ;

    AnimalState(String value) {
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
