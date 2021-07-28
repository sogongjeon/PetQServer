package com.sogong.sogong.repositories.animal

import com.sogong.sogong.model.animal.AnimalData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AnimalDataRepository : JpaRepository<AnimalData, Long> {
    @Query("select ad from AnimalData ad where ad.desertionNo = ?1")
    fun findByDesertionNo(desertionNo : String) : AnimalData?
}