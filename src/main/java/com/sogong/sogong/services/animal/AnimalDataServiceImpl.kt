package com.sogong.sogong.services.animal

import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.repositories.animal.AnimalDataRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
open class AnimalDataServiceImpl(private val repository: AnimalDataRepository) : BaseServiceImpl<AnimalData>(repository), AnimalDataService {
    override fun findByDesertionNo(desertionNo : String) : AnimalData? {
        return repository.findByDesertionNo(desertionNo)
    }
}