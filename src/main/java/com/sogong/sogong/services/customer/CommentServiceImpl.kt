package com.sogong.sogong.services.customer

import com.sogong.sogong.model.customer.Comment
import com.sogong.sogong.repositories.customer.CommentRepository
import com.sogong.sogong.services.generic.BaseServiceImpl
import org.springframework.stereotype.Service

@Service
open class CommentServiceImpl(private val repository : CommentRepository) : BaseServiceImpl<Comment>(repository), CommentService {
    override fun findByTypeAndId(type: String, id: Long): List<Comment> {
        return repository.findByTypeAndId(type, id)
    }
}
