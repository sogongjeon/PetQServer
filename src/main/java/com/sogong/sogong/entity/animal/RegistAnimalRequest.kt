package com.sogong.sogong.entity.animal

import org.springframework.web.multipart.MultipartFile

data class RegisterAnimalRequest (
        var protectType : String ?= null,
        var guCode : String ?= null,
        var animalKindCode : String ?= null,
        var happenedDate : String ?= null, //format: yyyyMMdd
        var happenedPlace : String ?= null,
        var color : String ?= null,
        var birthYear : String ?= null,
        var weight : String ?= null,
        var image : MultipartFile?= null,
        var sex : String ?= null,//F,M,Q
        var isNeutered : String ?= null,//N,Y,U
        var specialMark : String ?= null,
        var managerName : String ?= null,
        var managerTel : String ?= null
)
