package com.sogong.sogong.controller.animal

import com.sogong.sogong.entity.EntityList
import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.common.Criteria
import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.services.animal.AnimalDataService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/v1/animal")
class AnimalController(
        private val animalDataService: AnimalDataService,
        private val restTemplate: RestTemplate
) {
    private val log: Logger = LoggerFactory.getLogger(AnimalController::class.java)

    @PostMapping("/list")
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

}