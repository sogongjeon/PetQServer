package com.sogong.sogong.entity.animal

import com.sogong.sogong.entity.EntityList
import com.sogong.sogong.entity.customer.CommentEntity

class AnimalDetailEntity {
    var id: Long? = null //
    var protectType : String ?= null //보호소,발견,개인 보호
    var type : String ?= null//
    var image : String ?= null//
    var kindName : String ?= null//
    var dDay : String ?= null//
    var place : String ?= null//
    var sex : String ?= null//
    var isFavorite : Boolean ?= null//pass
    var desertionNo : String ?= null//
    var happenedDate : String ?= null//
    var happenedPlace : String ?= null//
    var color : String ?= null
    var birthYear : Int ?= null
    var weight : String ?= null
    var noticeNo : String ?= null
    var noticeStart : String ?= null
    var noticeEnd : String ?= null//
    var processState : String ?= null
    var isNeutered : String ?= null
    var specialMark : String ?= null
    var careName : String ?= null
    var careTel : String ?= null
    var careAddress : String ?= null
    var managerName : String ?= null
    var managerTel : String ?= null
    var comments : EntityList<CommentEntity>?= null
}
