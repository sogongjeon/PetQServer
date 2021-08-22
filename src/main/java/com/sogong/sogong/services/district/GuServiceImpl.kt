package com.sogong.sogong.services.district

import com.sogong.sogong.entity.district.GuInfo
import com.sogong.sogong.model.district.Gu
import com.sogong.sogong.repositories.district.GuRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class GuServiceImpl(private val repository : GuRepository) : BaseServiceImpl<Gu>(repository), GuService {
    override fun findByGuCode(gu : String) : Gu? {
        return repository.findByGuCode(gu)
    }

    override fun findByGuName(city : String, gu : String): Gu? {
        return repository.findByGuName(city, gu)
    }

    override fun getLastGuCode() : String {
        return repository.getLastGuCode()
    }

    override fun getGuInfo(guCode: String): GuInfo? {
        return repository.getGuInfo(guCode)
    }

    override fun listByCityCode(cityCode: String?): List<Gu>? {
        return repository.listByCityCode(cityCode)
    }
}
