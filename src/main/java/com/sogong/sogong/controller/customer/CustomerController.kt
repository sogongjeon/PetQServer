package com.sogong.sogong.controller.customer

import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.animal.PreferAnimalRequest
import com.sogong.sogong.entity.customer.LostPetEntity
import com.sogong.sogong.entity.customer.LostPetInfo
import com.sogong.sogong.entity.customer.LostPetRequest
import com.sogong.sogong.model.customer.LostPet
import com.sogong.sogong.services.animal.AnimalKindService
import com.sogong.sogong.services.customer.CustomerService
import com.sogong.sogong.services.customer.LostPetService
import com.sogong.sogong.services.district.GuService
import com.sogong.sogong.type.ApiResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.servlet.ServletContext

@RestController
@RequestMapping("/v1/customer")
class CustomerController(
        private val customerService: CustomerService,
        private val lostPetService: LostPetService,
        private val animalKindService: AnimalKindService,
        private val guService : GuService,
        private val servletContext: ServletContext
) {

    @Value("\${animalImage.lostAnimalSavePath}")
    private val lostAnimalSavePath = ""

    @Value("\${animalImage.getLostAnimalImagePath}")
    private val lostAnimalImagePath = ""


    @PostMapping("/prefer")
    fun preferAnimalKindRegister(@RequestBody animalRequest : PreferAnimalRequest) : ResultEntity<Boolean> {
        var customer = customerService.findByIdAndEnabledTrue(animalRequest.customerId!!)

        if(customer == null) {
            return ResultEntity("1500", "회원이 존재하지 않습니다.")
        }

        customer.preferredKindCode1 = animalRequest.kindCode1
        customer.preferredKindCode2 = animalRequest.kindCode2
        customer.preferredKindCode3 = animalRequest.kindCode3
        customer.preferredKindCode4 = animalRequest.kindCode4
        customer.preferredKindCode5 = animalRequest.kindCode5

        customerService.saveOrUpdate(customer)

        return ResultEntity(true)
    }

    @DeleteMapping("/prefer/{customerId}")
    fun deletePreferAnimalKind(@PathVariable customerId: Long) : ResultEntity<Boolean> {
        var customer = customerService.findByIdAndEnabledTrue(customerId)

        if(customer == null) {
            return ResultEntity("1500", "존재하지 않는 회원입니다.")
        }
        customer.preferredKindCode1 = null
        customer.preferredKindCode2 = null
        customer.preferredKindCode3 = null
        customer.preferredKindCode4 = null
        customer.preferredKindCode5 = null

        customerService.saveOrUpdate(customer)

        return ResultEntity(true)
    }

}
