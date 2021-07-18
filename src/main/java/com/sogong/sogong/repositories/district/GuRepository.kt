package com.sogong.sogong.repositories.district

import com.sogong.sogong.model.district.Gu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GuRepository : JpaRepository<Gu, Long> {

    @Query("select g from Gu g where g.guCode = ?1")
    fun findByGuCode(gu : String) : Gu?
}