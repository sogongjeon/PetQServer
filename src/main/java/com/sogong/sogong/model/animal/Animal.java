package com.sogong.sogong.model.animal;

import com.sogong.sogong.model.generic.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_ANIMAL")
public class Animal extends AuditableEntity {
    @Column
    private String animal;

    @Column
    private String kindCode;

    @Column
    private String kindName;


    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getKindCode() {
        return kindCode;
    }

    public void setKindCode(String kindCode) {
        this.kindCode = kindCode;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }
}
