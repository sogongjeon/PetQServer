package com.sogong.sogong.entity.customer

data class CommentRequest(
        var type : String ?= null,
        var dataId : Long ?= null,
        var customerId : Long ?= null,
        var comment : String ?= null
)
