package com.sogong.sogong.repositories.animal

import com.sogong.sogong.model.animal.AnimalKind
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

//@Repository
interface AnimalKindRepository : JpaRepository<AnimalKind, Long>, AnimalKindRepositoryCustom {
    @Query("select a from AnimalKind a where a.kindCode = ?1")
    fun findByTypeCode(code : String) : AnimalKind?

//    @Query("select a from AnimalKind a where a.type = ?1")
//    fun findByType(type : String?) : List<AnimalKind>?
}
