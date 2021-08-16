package com.sogong.sogong.model.animal;

import com.sogong.sogong.model.generic.AuditableEntity;
import com.sogong.sogong.type.animal.ProtectType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="TBL_ANIMAL_DATA")
public class AnimalData extends AuditableEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20)
    private ProtectType protectType;

    @Column
    private String guCode; //구 코드

    @Column
    private String animalKindCode; //동물 종류 코드

    @Column
    private String desertionNo; //유기 번호

    @Column
    private LocalDateTime happenedDate; //접수 일자

    @Column
    private String happenedPlace; //발견 장소

    @Column
    private String kindName; //종 이름

    @Column
    private String color; //색깔

    @Column
    private Integer birthYear; //출생년도

    @Column
    private String weight; //무게

    @Column
    private String noticeNo; //공고번호

    @Column
    private LocalDateTime noticeStart; //공고 시작일

    @Column
    private LocalDateTime noticeEnd; //공고 종료일

    @Column
    private String imageUrl; //이미지 url

    @Column
    private String processState; //공고 상태

    @Column
    private String sex; //성별(M:수컷, F:암컷, Q:미상)

    @Column
    private String isNeutered; //중성화여부(Y:예, N:아니요, U:미상)

    @Column
    private String specialMark; //특징

    @Column
    private String careName; //보호소이름

    @Column
    private String careTel; //보호소 전화번호

    @Column
    private String careAddress; //보호 장소

    @Column
    private String managerName; //담당자 이름 (chargeNm)

    @Column
    private String managerTel; //담당자 전화번호(officetel)


    public ProtectType getProtectType() {
        return protectType;
    }

    public void setProtectType(ProtectType protectType) {
        this.protectType = protectType;
    }

    public String getGuCode() {
        return guCode;
    }

    public void setGuCode(String guCode) {
        this.guCode = guCode;
    }

    public String getAnimalKindCode() {
        return animalKindCode;
    }

    public void setAnimalKindCode(String animalKindCode) {
        this.animalKindCode = animalKindCode;
    }

    public String getDesertionNo() {
        return desertionNo;
    }

    public void setDesertionNo(String desertionNo) {
        this.desertionNo = desertionNo;
    }

    public LocalDateTime getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(LocalDateTime happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getHappenedPlace() {
        return happenedPlace;
    }

    public void setHappenedPlace(String happenedPlace) {
        this.happenedPlace = happenedPlace;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public LocalDateTime getNoticeStart() {
        return noticeStart;
    }

    public void setNoticeStart(LocalDateTime noticeStart) {
        this.noticeStart = noticeStart;
    }

    public LocalDateTime getNoticeEnd() {
        return noticeEnd;
    }

    public void setNoticeEnd(LocalDateTime noticeEnd) {
        this.noticeEnd = noticeEnd;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIsNeutered() {
        return isNeutered;
    }

    public void setIsNeutered(String isNeutered) {
        this.isNeutered = isNeutered;
    }

    public String getSpecialMark() {
        return specialMark;
    }

    public void setSpecialMark(String specialMark) {
        this.specialMark = specialMark;
    }

    public String getCareName() {
        return careName;
    }

    public void setCareName(String careName) {
        this.careName = careName;
    }

    public String getCareTel() {
        return careTel;
    }

    public void setCareTel(String careTel) {
        this.careTel = careTel;
    }

    public String getCareAddress() {
        return careAddress;
    }

    public void setCareAddress(String careAddress) {
        this.careAddress = careAddress;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerTel() {
        return managerTel;
    }

    public void setManagerTel(String managerTel) {
        this.managerTel = managerTel;
    }
}
