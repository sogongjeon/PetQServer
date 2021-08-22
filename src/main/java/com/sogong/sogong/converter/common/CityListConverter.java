package com.sogong.sogong.converter.common;

import com.sogong.sogong.converter.AbstractDataConverter;
import com.sogong.sogong.entity.common.CityDataEntity;
import com.sogong.sogong.model.district.City;
import com.sogong.sogong.services.district.CityService;

public class CityListConverter extends AbstractDataConverter<City, CityDataEntity> {

    @Override
    protected CityDataEntity createTarget() {return new CityDataEntity();}

    @Override
    public CityDataEntity convert(City source, CityDataEntity target) {
        target.setCode(source.getCityCode());
        target.setName(source.getCityName());

        return target;
    }
}
