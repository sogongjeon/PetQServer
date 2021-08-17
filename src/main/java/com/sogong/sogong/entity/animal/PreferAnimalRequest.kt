package com.sogong.sogong.entity.animal

data class PreferAnimalRequest (
        var customerId : Long ?= null,
        var kindCode1 : String ?= null,
        var kindCode2 : String ?= null,
        var kindCode3 : String ?= null,
        var kindCode4 : String ?= null,
        var kindCode5 : String ?= null
)
