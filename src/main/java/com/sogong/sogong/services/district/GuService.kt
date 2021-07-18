package com.sogong.sogong.services.district

import com.sogong.sogong.model.district.Gu
import com.sogong.sogong.services.generic.BaseService
import org.springframework.stereotype.Service

//@Service
interface GuService : BaseService<Gu> {
    fun findByGuCode(gu : String) : Gu?
}