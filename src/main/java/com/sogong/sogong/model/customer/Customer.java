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
    private Long preferredKindId;

    @Column
    private String guCode;

    @Column
    private Boolean enabled;

    //회원 프로필 저장경로
    @Column
    private String profilePath;


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

    public Long getPreferredKindId() {
        return preferredKindId;
    }

    public void setPreferredKindId(Long preferredKindId) {
        this.preferredKindId = preferredKindId;
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
}
