package com.sogong.sogong.entity.customer

import org.springframework.web.multipart.MultipartFile

data class LostPetRequest (
        var customerId : Long ?= null,
        var animalKindId : Long ?= null,
        var image : MultipartFile ?= null,
        var guCode : String ?= null,
        var lostDay : String ?= null,
        var phoneNumber : String ?= null,
        var petNotes : String ?= null,
        var etcMemo : String ?= null,
)
