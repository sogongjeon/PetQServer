package com.sogong.sogong.controller.external

import com.animal.animal.entity.external.FindAbandonedResponse

interface PublicAbandonedService {

    fun findAnimalList() : FindAbandonedResponse?

}