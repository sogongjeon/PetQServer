package com.sogong.sogong.model.customer;

import com.sogong.sogong.model.generic.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_LOST_PET")
public class LostPet extends AuditableEntity {

    //회원 아이디
    @Column
    private Long customerId;

    //동물 품종
    @Column
    private Long animalKindId;

    //실종동물이미지 경로
    @Column
    private String imgPath;

    //실종위치 구코드
    @Column
    private String guCode;

    //보호자 전화번호
    @Column
    private String phoneNumber;

    //실종일자
    @Column
    private LocalDateTime lostDay;

    //동물 특징
    @Column
    private String petNotes;

    //기타 메모사항
    @Column
    private String etcMemo;


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAnimalKindId() {
        return animalKindId;
    }

    public void setAnimalKindId(Long animalKindId) {
        this.animalKindId = animalKindId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getGuCode() {
        return guCode;
    }

    public void setGuCode(String guCode) {
        this.guCode = guCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getLostDay() {
        return lostDay;
    }

    public void setLostDay(LocalDateTime lostDay) {
        this.lostDay = lostDay;
    }

    public String getPetNotes() {
        return petNotes;
    }

    public void setPetNotes(String petNotes) {
        this.petNotes = petNotes;
    }

    public String getEtcMemo() {
        return etcMemo;
    }

    public void setEtcMemo(String etcMemo) {
        this.etcMemo = etcMemo;
    }
}
