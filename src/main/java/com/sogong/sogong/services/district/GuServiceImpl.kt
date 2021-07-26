package com.sogong.sogong.services.district

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
}