package com.sogong.sogong.services.customer

import com.sogong.sogong.model.customer.Customer
import com.sogong.sogong.services.generic.BaseService

interface CustomerService : BaseService<Customer> {
    fun findByIdAndEnabledTrue(id : Long) : Customer?
}
