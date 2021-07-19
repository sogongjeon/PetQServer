package com.sogong.sogong.repositories.animal

import com.sogong.sogong.model.animal.Animal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AnimalRepository : JpaRepository<Animal, Long> {
    @Query("select a from Animal a where a.kindCode = ?1")
    fun findByKindCode(code : String) : Animal?
}