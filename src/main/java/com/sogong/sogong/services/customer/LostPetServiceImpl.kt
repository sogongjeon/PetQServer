package com.sogong.sogong.services.customer

import com.sogong.sogong.model.customer.LostPet
import com.sogong.sogong.repositories.customer.LostPetRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
open class LostPetServiceImpl(private val repository : LostPetRepository) : BaseServiceImpl<LostPet>(repository), LostPetService {
    override fun findByCustomerId(id : Long) : LostPet? {
        return repository.findByCustomerId(id)
    }
}
