package com.sogong.sogong.repositories.customer

import com.sogong.sogong.model.customer.LostPet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LostPetRepository : JpaRepository<LostPet, Long> {
    @Query("select l from LostPet l where l.customerId = ?1")
    fun findByCustomerId(id : Long) : LostPet?
}
