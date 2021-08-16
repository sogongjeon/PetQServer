package com.sogong.sogong.type.animal;

import com.sogong.sogong.type.EnumModel;

public enum AnimalKindType implements EnumModel {
    DOG("개"),
    CAT("고양이"),
    ETC("기타")
    ;

    AnimalKindType(String value) {
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
