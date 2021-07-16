package com.sogong.sogong.model.district;

import com.sogong.sogong.model.generic.AuditableEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_GU")
public class Gu extends AuditableEntity {

    private String orgCode;

    private String guName;

    private String guCode;


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getGuName() {
        return guName;
    }

    public void setGuName(String guName) {
        this.guName = guName;
    }

    public String getGuCode() {
        return guCode;
    }

    public void setGuCode(String guCode) {
        this.guCode = guCode;
    }
}
