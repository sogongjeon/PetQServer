package com.sogong.sogong.controller.common

import com.sogong.sogong.converter.common.CityListConverter
import com.sogong.sogong.converter.common.GuListConverter
import com.sogong.sogong.entity.EntityList
import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.common.CityDataEntity
import com.sogong.sogong.entity.common.GuDataEntity
import com.sogong.sogong.model.district.Gu
import com.sogong.sogong.services.district.CityService
import com.sogong.sogong.services.district.GuService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/common")
class CommonController (
        private val cityService: CityService,
        private val guService : GuService
) {
    @GetMapping("/city")
    fun getCity() : ResultEntity<EntityList<CityDataEntity>> {
        var cities = cityService.findAll()

        var result = EntityList<CityDataEntity>()
        var converter = CityListConverter()

        result.totalCount = cities.size.toLong()
        result.elements = cities.stream()
                .map(converter::convert)
                .toList()

        return ResultEntity(result)
    }

    @GetMapping("/gu")
    fun getGu(@RequestParam cityCode : String?) : ResultEntity<EntityList<GuDataEntity>>? {
        var guList : List<Gu>? = if(cityCode != null) {
            guService.listByCityCode(cityCode)
        } else {
            guService.findAll()
        }

        var result = EntityList<GuDataEntity>()
        var converter = GuListConverter()

        if(guList == null) {
            return null
        }

        result.totalCount = guList!!.size.toLong()
        result.elements = guList!!.stream()
                .map(converter::convert)
                .toList()

        return ResultEntity(result)
    }
}
