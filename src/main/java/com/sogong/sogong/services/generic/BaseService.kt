package com.sogong.sogong.services.generic

import com.sogong.sogong.model.generic.BaseEntity
import org.springframework.transaction.annotation.Transactional
import java.util.*


interface BaseService<T : BaseEntity> {
    @Transactional
    fun update(entity: T)

    @Transactional
    fun create(entity: T)

    @Transactional
    fun delete(entity: T)

    @Transactional
    fun saveOrUpdate(entity: T)

    @Transactional
    fun create(iterable: Iterable<T>)

    @Transactional
    fun delete(iterable: Iterable<T>)


    @Transactional(readOnly = true)
    fun findByIdReadOnly(id: Long?): Optional<T>

    @Transactional
    fun findById(id: Long?): Optional<T>

    @Transactional(readOnly = true)
    fun findAll(): List<T>

    @Transactional(readOnly = true)
    fun count(): Long?

    fun flush()
}

