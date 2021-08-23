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


    @PostMapping("/lostpet/register")
    fun lostPetRegister(@ModelAttribute lostPetRequest : LostPetRequest) : ResultEntity<Boolean> {
        val customer = customerService.findByIdAndEnabledTrue(lostPetRequest.customerId!!)
                ?: return ResultEntity(ApiResult.NOT_FOUND, "회원이 존재하지 않습니다.")

        val lostPet = lostPetService.findByCustomerId(lostPetRequest.customerId!!)
        if(lostPet != null) {
            lostPet.customerId = lostPetRequest.customerId
            lostPet.animalKindId = lostPetRequest.animalKindId
            lostPet.guCode = lostPetRequest.guCode
            lostPet.phoneNumber = lostPetRequest.phoneNumber
            lostPet.lostDay = LocalDate.parse(lostPetRequest.lostDay, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0,0,0);
            lostPet.petNotes = lostPetRequest.petNotes
            lostPet.etcMemo = lostPetRequest.etcMemo

            lostPetService.saveOrUpdate(lostPet)
            if(lostPetRequest.image == null) {
                return ResultEntity("1500","이미지 없으면 안돼")
            }
            try {
                var webAppRoot = servletContext.getRealPath("/")
                var relativeFolder = "/resources/images/animal-image/"

                val dest = File(webAppRoot+relativeFolder+lostPet.id+".jpg")

//                FileCopyUtils.copy(file.getBytes(), new File(filename));
                FileCopyUtils.copy(lostPetRequest.image!!.bytes, dest)
//                lostPetRequest.image!!.transferTo(dest)

                println("${dest}로 실종동물 이미지 저장완료!")
            } catch(e : IllegalStateException ) {
                e.printStackTrace();
            } catch (e : IOException) {
                e.printStackTrace();
            }
        } else {
            val newLostPet = LostPet()
            newLostPet.customerId = lostPetRequest.customerId
            newLostPet.animalKindId = lostPetRequest.animalKindId
            newLostPet.guCode = lostPetRequest.guCode
            newLostPet.phoneNumber = lostPetRequest.phoneNumber
            newLostPet.lostDay = LocalDate.parse(lostPetRequest.lostDay, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0, 0, 0);
            newLostPet.petNotes = lostPetRequest.petNotes
            newLostPet.etcMemo = lostPetRequest.etcMemo

            lostPetService.saveOrUpdate(newLostPet)

            try {
                var webAppRoot = servletContext.getRealPath("/")
                var relativeFolder = "/resources/images/animal-image/"

                val dest = File(webAppRoot+relativeFolder+newLostPet.id+".jpg")

//                if (!dest.exists()) {
//                    //디렉토리 생성 메서드
//                    dest.mkdirs()
//                }
//                lostPetRequest.image!!.transferTo(dest)
                FileCopyUtils.copy(lostPetRequest.image!!.bytes, dest)
                println("${dest}로 실종동물 이미지 저장완료!")
            } catch(e : IllegalStateException ) {
                e.printStackTrace();
            } catch (e : IOException) {
                e.printStackTrace();
            }
        }

        return ResultEntity(true)
    }

    // 실종동물 삭제 API
    @DeleteMapping("/lostpet/delete/{id}")
    fun deleteLostPet(@PathVariable id: Long) : ResultEntity<Boolean> {
        //존재하는지 확인 후 삭제, 없으면 false
        val lostPet = lostPetService.findById(id).orElse(null) ?: return ResultEntity(ApiResult.NOT_FOUND)

        lostPetService.delete(lostPet)

        return ResultEntity(true)
    }

    // 회원아이디로 실종동물 데이터 정보를 가져오는 api
    @GetMapping("/lostpet/{id}")
    fun lostPetData(@PathVariable id: Long): ResultEntity<LostPetEntity> {
        var lostPet = lostPetService.findByCustomerId(id)
        var animalKind = animalKindService.findById(lostPet!!.animalKindId).orElse(null)
        var guInfo = guService.getGuInfo(lostPet.guCode)

        if(lostPet == null) {
            return ResultEntity(ApiResult.NOT_FOUND,"실종 동물 데이터가 존재하지 않습니다.")
        }

        if(animalKind == null) {
            return ResultEntity(ApiResult.NOT_FOUND, "해당 품종 정보를 가져오는데 실패했습니다.")
        }

        var petInfo = LostPetInfo()
        petInfo.id = lostPet.id
        petInfo.lostDay = lostPet.lostDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        petInfo.imgPath = lostAnimalImagePath+lostPet.id
        petInfo.animalKind = animalKind.type
        petInfo.animalKindDetail = animalKind.kindName
        petInfo.city = guInfo!!.city + " " + guInfo.gu
        petInfo.petNotes = lostPet.petNotes
        petInfo.etcMemo = lostPet.etcMemo
        petInfo.phoneNumber = lostPet.phoneNumber

        var lostPetEntity = LostPetEntity()
        lostPetEntity.lostPetInfo = petInfo

        return ResultEntity(lostPetEntity)
    }

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
