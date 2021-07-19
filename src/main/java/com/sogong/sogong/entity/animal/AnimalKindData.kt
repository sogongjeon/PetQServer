package com.sogong.sogong.entity.animal


data class AnimalKindResponse (
        var response : AnimalKindResponseDetail?= null
)

data class AnimalKindResponseDetail (
        var header : AnimalKindResponseHeader?= null,
        var body : AnimalKindResponseBody?= null
)

data class AnimalKindResponseHeader (
        var resultCode : String ?= null, //결과 코드
        var resultMsg : String ?= null, //결과 메세지
)

data class AnimalKindResponseBody (
        var items : AnimalKindResponseItems?= null
)

data class AnimalKindResponseItems (
        var item : List<AnimalKindResponseItem> ?= null
)

data class AnimalKindResponseItem (
        var KNm : String ?= null,
        var kindCd : String ?= null
)
