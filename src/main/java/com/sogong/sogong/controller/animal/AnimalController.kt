package com.sogong.sogong.controller.animal

import com.sogong.sogong.entity.EntityList
import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.animal.AnimalKindDetailDto
import com.sogong.sogong.entity.animal.AnimalSearchCriteria
import com.sogong.sogong.entity.animal.RegisterAnimalRequest
import com.sogong.sogong.entity.common.Criteria
import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.services.animal.AnimalDataService
import com.sogong.sogong.services.animal.AnimalKindService
import com.sogong.sogong.type.animal.AnimalKindType
import com.sogong.sogong.type.animal.ProtectType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/v1/animal")
class AnimalController(
        private val animalDataService: AnimalDataService,
        private val restTemplate: RestTemplate,
        private val animalKindService: AnimalKindService
) {
    private val log: Logger = LoggerFactory.getLogger(AnimalController::class.java)

    @GetMapping("/list")
    fun animalList(@RequestParam(required = false, defaultValue = "0") page: Int,
                   @RequestParam(required = false, defaultValue = "20") size: Int) : ResultEntity<EntityList<AnimalData>> {

        val criteria = Criteria()
        criteria.page = page
        criteria.size = size

        var animalPage = animalDataService.listByCriteria(criteria)

        val animalResult = EntityList<AnimalData>()
        animalResult.totalCount = animalPage.totalElements
        animalResult.elements = animalPage
                .toList()

        return ResultEntity(animalResult)
    }

    @GetMapping("/list2")
    fun animalListVer2(@ModelAttribute criteria : AnimalSearchCriteria) : ResultEntity<EntityList<AnimalData>> {

//        val animalCriteria = AnimalSearchCriteria()

        var animalPage = animalDataService.listByAnimalCriteria(criteria)

        val animalResult = EntityList<AnimalData>()
        animalResult.totalCount = animalPage!!.totalElements
        animalResult.elements = animalPage
                .toList()

        return ResultEntity(animalResult)
    }

    @GetMapping("/kind")
    fun getAnimalKind(@RequestParam(value="type", required=false) type : String?) : ResultEntity<EntityList<AnimalKindDetailDto>> {
        var animalKindList = animalKindService.findByType(type)

        var animalKindDto : ArrayList<AnimalKindDetailDto> = ArrayList()
        val result = EntityList<AnimalKindDetailDto>()

        if(animalKindList != null) {
            for(animalKind in animalKindList) {
                var animalKindDetail = AnimalKindDetailDto()
                animalKindDetail.kind = AnimalKindType.valueOf(animalKind.type).value
                animalKindDetail.kindDetailName = animalKind.kindName
                animalKindDetail.kindDetailCode = animalKind.kindCode

                animalKindDto.add(animalKindDetail)
            }
            result.elements = animalKindDto
            result.totalCount = animalKindDto.size.toLong()

        }
        return ResultEntity(result)
    }

    @PostMapping("/register")
    fun findAnimalRegister(@RequestBody request : RegisterAnimalRequest) : ResultEntity<Boolean> {
        var animalKind = animalKindService.findByTypeCode(request.animalKindCode!!)

        var newAnimalData = AnimalData()

        newAnimalData.protectType = ProtectType.valueOf(request.protectType!!)
        newAnimalData.guCode = request.guCode
        newAnimalData.animalKindCode = request.animalKindCode
        newAnimalData.happenedDate = LocalDate.parse(request.happenedDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0, 0, 0);
        newAnimalData.happenedPlace = request.happenedPlace
        newAnimalData.kindName = "[${AnimalKindType.valueOf(animalKind!!.type).value}] ${animalKind!!.kindName}"
        newAnimalData.color = request.color
        newAnimalData.birthYear = request.birthYear!!.toInt()
        newAnimalData.weight = request.weight
        newAnimalData.imageUrl = request.imageUrl
        newAnimalData.processState = "보호중"
        newAnimalData.sex = request.sex
        newAnimalData.isNeutered = request.isNeutered
        newAnimalData.specialMark = request.specialMark
        newAnimalData.managerName = request.managerName
        newAnimalData.managerTel = request.managerTel

        animalDataService.saveOrUpdate(newAnimalData)

        return ResultEntity(true)
    }

}
