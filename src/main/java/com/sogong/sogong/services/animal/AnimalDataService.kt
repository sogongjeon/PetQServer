package com.sogong.sogong.services.animal

import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.services.generic.BaseService

interface AnimalDataService : BaseService<AnimalData> {
    fun findByDesertionNo(desertionNo : String) : AnimalData?
}