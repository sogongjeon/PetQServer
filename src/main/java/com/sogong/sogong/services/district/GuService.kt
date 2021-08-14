package com.sogong.sogong.services.district

import com.sogong.sogong.entity.district.GuInfo
import com.sogong.sogong.model.district.Gu
import com.sogong.sogong.services.generic.BaseService
import org.springframework.stereotype.Service

//@Service
interface GuService : BaseService<Gu> {
    fun findByGuCode(gu : String) : Gu?

    fun findByGuName(city : String, gu : String) : Gu?

    fun getLastGuCode() : String

    fun getGuInfo(guCode : String) : GuInfo?
}
