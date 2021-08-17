package com.sogong.sogong.services.animal

import com.sogong.sogong.entity.animal.AnimalSearchCriteria
import com.sogong.sogong.entity.common.Criteria
import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.repositories.animal.AnimalDataRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page

@Service
open class AnimalDataServiceImpl(private val repository: AnimalDataRepository) : BaseServiceImpl<AnimalData>(repository), AnimalDataService {
    override fun findByDesertionNo(desertionNo : String) : AnimalData? {
        return repository.findByDesertionNo(desertionNo)
    }

    override fun listByCriteria(criteria : Criteria) : Page<AnimalData> {
        return repository.listByCriteria(criteria)
    }

    override fun listByAnimalCriteria(criteria: AnimalSearchCriteria): Page<AnimalData>? {
        return repository.listByAnimalCriteria(criteria)
    }
}
