package com.sogong.sogong.converter.common;

import com.sogong.sogong.converter.AbstractDataConverter;
import com.sogong.sogong.entity.common.CityDataEntity;
import com.sogong.sogong.entity.common.GuDataEntity;
import com.sogong.sogong.model.district.City;
import com.sogong.sogong.model.district.Gu;
import com.sogong.sogong.services.district.CityService;

public class GuListConverter extends AbstractDataConverter<Gu, GuDataEntity> {

    @Override
    protected GuDataEntity createTarget() {return new GuDataEntity();}

    @Override
    public GuDataEntity convert(Gu source, GuDataEntity target) {
        target.setCityCode(source.getCityCode());
        target.setCityName(source.getCityName());
        target.setGuCode(source.getGuCode());
        target.setGuName(source.getGuName());

        return target;
    }
}
