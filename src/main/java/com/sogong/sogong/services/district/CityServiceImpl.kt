package com.sogong.sogong.services.district

import com.sogong.sogong.model.district.City
import com.sogong.sogong.repositories.district.CityRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
open class CityServiceImpl(private val repository: CityRepository) : BaseServiceImpl<City>(repository), CityService{
    override fun findByCityCode(code : String) : City? {
        return repository.findByCityCode(code)
    }

    override fun findByCityName(name: String): City? {
        return repository.findByCityName(name)
    }

}
