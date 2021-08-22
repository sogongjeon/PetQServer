package com.sogong.sogong.services.district

import com.sogong.sogong.model.district.City
import com.sogong.sogong.services.generic.BaseService

interface CityService : BaseService<City> {
    fun findByCityCode(code : String) : City?

    fun findByCityName(name : String) : City?
}
