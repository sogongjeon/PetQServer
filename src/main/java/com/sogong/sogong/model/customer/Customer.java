package com.sogong.sogong.model.customer;

import com.sogong.sogong.model.generic.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUSTOMER")
public class Customer extends AuditableEntity {

    //회원 이메일(아이디로 사용)
    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private String guCode;

    @Column(length = 1)
    private Boolean enabled = true;

    //회원 프로필 저장경로
    @Column
    private String profilePath;

    @Column
    private String preferredKindCode1;

    @Column
    private String preferredKindCode2;

    @Column
    private String preferredKindCode3;

    @Column
    private String preferredKindCode4;

    @Column
    private String preferredKindCode5;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGuCode() {
        return guCode;
    }

    public void setGuCode(String guCode) {
        this.guCode = guCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getPreferredKindCode1() {
        return preferredKindCode1;
    }

    public void setPreferredKindCode1(String preferredKindCode1) {
        this.preferredKindCode1 = preferredKindCode1;
    }

    public String getPreferredKindCode2() {
        return preferredKindCode2;
    }

    public void setPreferredKindCode2(String preferredKindCode2) {
        this.preferredKindCode2 = preferredKindCode2;
    }

    public String getPreferredKindCode3() {
        return preferredKindCode3;
    }

    public void setPreferredKindCode3(String preferredKindCode3) {
        this.preferredKindCode3 = preferredKindCode3;
    }

    public String getPreferredKindCode4() {
        return preferredKindCode4;
    }

    public void setPreferredKindCode4(String preferredKindCode4) {
        this.preferredKindCode4 = preferredKindCode4;
    }

    public String getPreferredKindCode5() {
        return preferredKindCode5;
    }

    public void setPreferredKindCode5(String preferredKindCode5) {
        this.preferredKindCode5 = preferredKindCode5;
    }
}
