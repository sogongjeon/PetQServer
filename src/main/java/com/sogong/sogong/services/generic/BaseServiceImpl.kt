package com.sogong.sogong.services.generic

import com.sogong.sogong.model.generic.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

open class BaseServiceImpl<T : BaseEntity>(private val repository: JpaRepository<T, Long>) : BaseService<T> {

    @Transactional
    override fun update(entity: T) {
        repository.save(entity)
    }

    @Transactional
    override fun create(entity: T) {
        repository.saveAndFlush(entity)
    }

    @Transactional
    override fun delete(entity: T) {
        repository.delete(entity)
    }

    @Transactional
    override fun saveOrUpdate(entity: T) {
        if (entity.isNew) {
            create(entity)
        } else {
            update(entity)
        }
    }

    @Transactional
    override fun create(iterable: Iterable<T>) {
        repository.saveAll(iterable)
    }

    @Transactional
    override fun delete(iterable: Iterable<T>) {
        repository.deleteInBatch(iterable)
    }

    @Transactional(readOnly = true)
    override fun findByIdReadOnly(id: Long?): Optional<T> {
        return id?.let { repository.findById(it) } as Optional<T>
    }

    @Transactional
    override fun findById(id: Long?): Optional<T> {
        return id?.let { repository.findById(it) } as Optional<T>
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<T> {
        return repository.findAll()
    }

    @Transactional(readOnly = true)
    override fun count(): Long? {
        return repository.count()
    }

    override fun flush() {
        repository.flush()
    }
}
