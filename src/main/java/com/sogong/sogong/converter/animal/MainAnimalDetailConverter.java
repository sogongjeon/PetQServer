package com.sogong.sogong.converter.animal;

import com.sogong.sogong.converter.AbstractDataConverter;
import com.sogong.sogong.entity.animal.AnimalDataEntity;
import com.sogong.sogong.entity.animal.AnimalDetailEntity;
import com.sogong.sogong.entity.district.GuInfo;
import com.sogong.sogong.model.animal.AnimalData;
import com.sogong.sogong.model.animal.AnimalKind;
import com.sogong.sogong.services.animal.AnimalKindService;
import com.sogong.sogong.services.district.GuService;
import org.springframework.core.convert.ConversionException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MainAnimalDetailConverter extends AbstractDataConverter<AnimalData, AnimalDetailEntity> {
    private final AnimalKindService animalKindService;
    private final GuService guService;
    private final String imagePath;

    public MainAnimalDetailConverter(String imagePath, AnimalKindService animalKindService, GuService guService) {
        this.imagePath = imagePath;
        this.animalKindService = animalKindService;
        this.guService = guService;
    }

    @Override
    protected AnimalDetailEntity createTarget() {return new AnimalDetailEntity();}


    @Override
    public AnimalDetailEntity convert(AnimalData source, AnimalDetailEntity target) throws ConversionException, IOException {
        AnimalKind animalKind = animalKindService.findByTypeCode(source.getAnimalKindCode());

        if(animalKind == null){
            return null;
        }
        target.setId(source.getId());
        target.setProtectType(source.getProtectType().getValue());
        target.setType(animalKind.getType());
        target.setImage(imagePath+source.getId());
        target.setKindName(source.getKindName());
        if(source.getNoticeEnd() != null) {
            Long dayDiff = ChronoUnit.DAYS.between(LocalDateTime.now(), source.getNoticeEnd());
            Integer dDay = 0;
            //당일이거나 이미 지난 경우엔 dDay 0으로 처리
            if (dayDiff > 0) {
                dDay = Math.toIntExact(dayDiff + 1);
            }
            target.setDDay("D-" + dDay);
        }
        GuInfo guInfo = guService.getGuInfo(source.getGuCode());
        target.setPlace(guInfo.getCity()+" "+guInfo.getGu());
        String sex = "Q";

        switch(source.getSex()) {
            case "F" :
                sex = "여";
                break;
            case "M" :
                sex = "남";
                break;
            default :
                sex = "미상";
                break;
        }
        target.setSex(sex);
        target.setDesertionNo(source.getDesertionNo());
        target.setHappenedDate(source.getHappenedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        target.setHappenedPlace(source.getHappenedPlace());
        target.setColor(source.getColor());
        target.setBirthYear(source.getBirthYear());
        target.setWeight(source.getWeight());
        target.setNoticeNo(source.getNoticeNo());
        target.setNoticeStart(source.getNoticeStart().format((DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        target.setNoticeEnd(source.getNoticeEnd().format((DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        target.setProcessState(source.getProcessState());
        target.setNeutered(source.getIsNeutered());
        target.setSpecialMark(source.getSpecialMark());
        target.setCareName(source.getCareName());
        target.setCareTel(source.getCareTel());
        target.setCareAddress(source.getCareAddress());
        target.setManagerName(source.getManagerName());
        target.setManagerTel(source.getManagerTel());

        return target;
    }
}
