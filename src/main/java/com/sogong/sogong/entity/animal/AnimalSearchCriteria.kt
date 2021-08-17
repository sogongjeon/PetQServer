package com.sogong.sogong.entity.animal

import com.sogong.sogong.entity.common.Criteria
import com.sogong.sogong.type.animal.ProtectType

class AnimalSearchCriteria : Criteria() {
    var protectType : ProtectType?= null
    var cityCode : String ?= null
    var guCode : List<String> ?= null //null이면 전체검색?
    var sex : String ?= null
    var kind : List<String> ?= null
    var kindDetail : List<String> ?= null
}
