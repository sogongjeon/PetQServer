package com.sogong.sogong.services.animal

import com.sogong.sogong.model.animal.Animal
import com.sogong.sogong.repositories.animal.AnimalRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
open class AnimalServiceImpl(private val repository : AnimalRepository) : BaseServiceImpl<Animal>(repository), AnimalService {
    override fun findByKindCode(code: String): Animal? {
        return repository.findByKindCode(code)
    }
}