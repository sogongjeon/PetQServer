package com.sogong.sogong.entity.customer

data class LostPetRequest (
        var customerId : Long ?= null,
        var animalKindId : Long ?= null,
        var imgPath : String ?= null,
        var guCode : String ?= null,
        var lostDay : String ?= null,
        var phoneNumber : String ?= null,
        var petNotes : String ?= null,
        var etcMemo : String ?= null,
)
