package com.sogong.sogong.repositories.district

import com.sogong.sogong.model.district.Gu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GuRepository : JpaRepository<Gu, Long> {

    @Query("select g from Gu g where g.guCode = ?1")
    fun findByGuCode(gu : String) : Gu?

    @Query("select g from Gu g join City c on g.cityCode = c.cityCode where g.guName = ?2 and c.cityName = ?1")
    fun findByGuName(city : String, gu : String) : Gu?

    @Query("select MAX(g.guCode) from Gu g")
    fun getLastGuCode() : String
}