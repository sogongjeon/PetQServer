package com.sogong.sogong.services.customer

import com.sogong.sogong.model.customer.Customer
import com.sogong.sogong.repositories.customer.CustomerRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
open class CustomerServiceImpl(private val repository : CustomerRepository) : BaseServiceImpl<Customer>(repository), CustomerService{
    override fun findByIdAndEnabledTrue(id: Long): Customer? {
        return repository.findByIdAndEnabledTrue(id)
    }
}
