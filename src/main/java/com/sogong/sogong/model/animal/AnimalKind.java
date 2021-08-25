package com.sogong.sogong.model.animal;

import com.sogong.sogong.model.generic.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_ANIMAL_KIND")
public class AnimalKind extends AuditableEntity {
    @Column
    private String type;

    @Column
    private String kindCode;

    @Column
    private String kindName;

    @Column(length = 1)
    private Boolean enabled = true;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
