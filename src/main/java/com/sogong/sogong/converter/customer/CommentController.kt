package com.sogong.sogong.converter.customer

import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.customer.CommentRequest
import com.sogong.sogong.model.customer.Comment
import com.sogong.sogong.services.customer.CommentService
import com.sogong.sogong.services.customer.CustomerService
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/v1/comment")
class CommentController(
        private val commentService : CommentService,
        private val customerService: CustomerService
) {
    @PostMapping("/register")
    fun commentRegister(@RequestBody commentRequest : CommentRequest) : ResultEntity<Boolean> {
        if(!(commentRequest.type.equals("LOST_PET") || commentRequest.type.equals("ANIMAL_DATA"))) {
            return ResultEntity("1500", "type에는 LOST_PET(실종애완동물)과 ANIMAL_DATA(보호동물)만 들어갈 수 있습니다.")
        }
        if(customerService.findByIdAndEnabledTrue(commentRequest.customerId!!) == null) {
            return ResultEntity("1500","회원이 존재하지 않습니다.")
        }

        var comment = Comment()
        comment.type = commentRequest.type
        comment.dataId = commentRequest.dataId
        comment.customerId = commentRequest.customerId
        comment.comment = commentRequest.comment
        comment.createdAt = LocalDateTime.now()

        commentService.saveOrUpdate(comment)

        return ResultEntity(true)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId : Long) : ResultEntity<Boolean> {

        var comment = commentService.findById(commentId).orElse(null)
        if(comment == null) {
            return ResultEntity("1500", "해당 댓글이 존재하지 않습니다.")
        }

        commentService.delete(comment)

        return ResultEntity(true)
    }
}
