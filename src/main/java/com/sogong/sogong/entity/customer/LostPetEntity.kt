package com.sogong.sogong.entity.customer

data class LostPetEntity (
        var lostPetInfo : LostPetInfo ?= null,
)

data class LostPetInfo (
        var id : Long ?= null,
        var lostDay : String ?= null,
        var imgPath : String ?= null,
        var animalKind : String ?= null,
        var animalKindDetail : String ?= null,
        var phoneNumber : String ?= null,
        var city : String ?= null,
        var petNotes : String ?= null,
        var etcMemo : String ?= null
)
