package com.sogong.sogong.entity.animal

import org.springframework.web.multipart.MultipartFile

class AnimalDataEntity {
    var id: Long ?= null
    var type : String ?= null
    var image: String ?= null
    var kindName : String ?= null
    var dDay : String ?= null
    var place : String ?= null
    var date : String ?= null
    var sex : String ?= null
    var isFavorite: Boolean ?= null
}

