package com.sogong.sogong.repositories.customer

import com.sogong.sogong.model.customer.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.type = ?1 and c.dataId = ?2 order by c.createdAt")
    fun findByTypeAndId(type : String, id : Long) : List<Comment>
}
