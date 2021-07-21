package com.sogong.sogong.services.animal

import com.sogong.sogong.model.animal.AnimalKind
import com.sogong.sogong.services.generic.BaseService

interface AnimalKindService : BaseService<AnimalKind> {
    fun findByTypeCode(code : String) : AnimalKind?
}