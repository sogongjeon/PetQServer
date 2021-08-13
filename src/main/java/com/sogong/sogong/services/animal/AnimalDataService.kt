package com.sogong.sogong.services.animal

import com.sogong.sogong.entity.common.Criteria
import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.services.generic.BaseService
import org.springframework.data.domain.Page

interface AnimalDataService : BaseService<AnimalData> {
    fun findByDesertionNo(desertionNo : String) : AnimalData?

    fun listByCriteria(criteria : Criteria) : Page<AnimalData>
}