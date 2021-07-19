package com.sogong.sogong.services.animal

import com.sogong.sogong.model.animal.Animal
import com.sogong.sogong.services.generic.BaseService

interface AnimalService : BaseService<Animal> {
    fun findByKindCode(code : String) : Animal?
}