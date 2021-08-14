package com.sogong.sogong.services.customer

import com.sogong.sogong.model.customer.LostPet
import com.sogong.sogong.services.generic.BaseService

interface LostPetService : BaseService<LostPet> {
    fun findByCustomerId(id : Long) : LostPet?
}
