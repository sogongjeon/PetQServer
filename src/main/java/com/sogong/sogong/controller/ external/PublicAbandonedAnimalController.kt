package com.sogong.sogong.controller.external

//import com.animal.animal.services.external.PublicAbandonedService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/external/public/abandoned")
class PublicAbandonedAnimalController(
        private val publicAbandonedService: PublicAbandonedService
) {

    @PostMapping("/")
    fun default(): String {
        println("dkssud")
        return "hi"
    }

    @PostMapping("/find")
    fun findAnimal(): String {
        println("dkssud")
//        @RequestBody findRequest: FindAbandonedRequest
        var response = publicAbandonedService.findAnimalList()
        return response.toString()
    }
}