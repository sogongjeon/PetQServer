package com.sogong.sogong.repositories.customer

import com.sogong.sogong.model.customer.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer,Long> {
    @Query("select c from Customer c where c.id = ?1 and c.enabled = true")
    fun findByIdAndEnabledTrue(id : Long) : Customer?
}
