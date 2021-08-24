package com.sogong.sogong.services.customer

import com.sogong.sogong.model.customer.Comment
import com.sogong.sogong.services.generic.BaseService

interface CommentService : BaseService<Comment> {
    fun findByTypeAndId(type : String, id : Long) : List<Comment>
}
