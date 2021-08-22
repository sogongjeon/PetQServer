package com.sogong.sogong.entity.common

data class CityDataEntity (
        var name : String ?= null,
        var code : String ?= null
)

data class GuDataEntity(
        var cityName : String ?= null,
        var cityCode : String ?= null,
        var guName : String ?= null,
        var guCode : String ?= null
)
