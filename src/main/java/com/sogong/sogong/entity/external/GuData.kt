package com.sogong.sogong.entity.external

import com.animal.animal.entity.external.*
import com.animal.animal.entity.external.PublicResponseItems

data class GuResponse (
        var response : GuResponseDetail?= null
)

data class GuResponseDetail (
        var header : GuResponseHeader?= null,
        var body : GuResponseBody?= null
)

data class GuResponseHeader (
        var resultCode : String ?= null, //결과 코드
        var resultMsg : String ?= null, //결과 메세지
)

data class GuResponseBody (
        var items : GuResponseItems?= null
)

data class GuResponseItems (
        var item : List<GuResponseItem> ?= null
)

data class GuResponseItem (
        var orgCd : String ?= null, //구 코드
        var orgdownNm : String ?= null, //구 이름
        var uprCd : String ?= null //시 코드
)