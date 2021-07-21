package com.sogong.sogong.repositories.animal

import com.sogong.sogong.model.animal.AnimalKind
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AnimalKindRepository : JpaRepository<AnimalKind, Long> {
    @Query("select a from AnimalKind a where a.kindCode = ?1")
    fun findByTypeCode(code : String) : AnimalKind?
}