package com.sogong.sogong.services.animal

import com.sogong.sogong.model.animal.AnimalKind
import com.sogong.sogong.repositories.animal.AnimalKindRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
open class AnimalKindServiceImpl(private val repository : AnimalKindRepository) : BaseServiceImpl<AnimalKind>(repository), AnimalKindService {
    override fun findByTypeCode(code: String): AnimalKind? {
        return repository.findByTypeCode(code)
    }

    override fun findByType(type: String?): List<AnimalKind>? {
        return repository.listByType(type)
    }
}
