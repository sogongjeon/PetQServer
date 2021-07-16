package com.animal.animal.entity.external

data class FindAbandonedRequest (
        var searchFrom : String ?= null,
        var searchTo : String ?= null,
        var kind : String ?= null,
        var kindDetail : String ?= null,
        var sido : String ?= null,
        var org : String ?= null,
        var state : String ?= null,
        var neuter : String ?= null
)

data class FindAbandonedResponse (
        var response : PublicResponse ?= null
)

data class PublicResponse (
        var header : PublicResponseHeader ?= null,
        var body : PublicResponseBody ?= null
)

data class PublicResponseHeader (
        var resultCode : String ?= null, //결과 코드
        var resultMsg : String ?= null, //결과 메세지
)

data class PublicResponseBody (
        var items : PublicResponseItems ?= null,
        var numOfRows : String ?= null, //한 페이지 결과 수
        var pageNo : String ?= null, //페이지 번호
        var totalCount : String ?= null //전체 결과 수
)

data class PublicResponseItems (
        var item : List<PublicResponseItem> ?= null
)

data class PublicResponseItem (
        var desertionNo : String ?= null, //유기 번호
        var filename : String ?= null, //썸네일 이미지
        var happenDt : String ?= null, //접수 일자
        var happenPlace : String ?= null, //발견 장소
        var kindCd : String ?= null, //품종(ex)개,믹스견)
        var colorCd : String ?= null, //색상
        var age : String ?= null, //나이
        var weight : String ?= null, //체중
        var noticeNo : String ?= null, //공고번호
        var noticeSdt : String ?= null, //공고 시작일
        var noticeEdt : String ?= null, //공고 종료일
        var popfile : String ?= null, //이미지
        var processState : String ?= null, //상태
        var sexCd : String ?= null, //성별(M:수컷, F:암컷, Q:미상)
        var neuterYn : String ?= null, //중성화여부(Y:예, N:아니요, U:미상)
        var specialMark : String ?= null, //특징
        var careNm : String ?= null, //보호소이름
        var careTel : String ?= null, //보호소 전화번호
        var careAddr : String ?= null, //보호 장소
        var orgNm : String ?= null, //관할기관
        var chargeNm : String ?= null, //담당자
        var officetel : String ?= null, //담당자 연락처
        var noticeComment : String ?= null //특이사항
)