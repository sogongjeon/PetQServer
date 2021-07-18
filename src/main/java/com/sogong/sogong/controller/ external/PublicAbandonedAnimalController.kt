package com.sogong.sogong.controller.` external`

//import com.animal.animal.services.external.PublicAbandonedService
import com.sogong.sogong.config.PublicAbandonedConfig
import com.sogong.sogong.controller.external.PublicAbandonedService
import com.sogong.sogong.entity.external.GuResponse
import com.sogong.sogong.model.district.City
import com.sogong.sogong.model.district.Gu
import com.sogong.sogong.services.district.CityService
import com.sogong.sogong.services.district.GuService
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/v1/external/public/abandoned")
class PublicAbandonedAnimalController(
        private val publicAbandonedService: PublicAbandonedService,
        private val guService: GuService,
        private val cityService: CityService,
        private val publicAbandonedConfig: PublicAbandonedConfig,
        private val restTemplate: RestTemplate
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

    //구 정보 업데이트 하고싶을때 사용할것
    @PostMapping("/update-gu")
    fun updateGu() : Boolean {
        val cities: List<City> = cityService.findAll()


        val url: String = publicAbandonedConfig.sigunguUrl

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        for (city in cities) {
            println("city OrgName :  " + city.orgName)
            try {
                val builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                        .queryParam("upr_cd", city.orgCode)
                        .build(true)
                val uri = URI(builder.toUriString())
                val response: HttpEntity<GuResponse> = restTemplate.exchange<GuResponse>(
                        uri,
                        HttpMethod.GET,
                        HttpEntity<String>(headers),
                        GuResponse::class.java)
                if (response.body.response!!.body!!.items.toString() == "" || response.body.response!!.body == null) {
                    println("no data, in " + city.orgName)
                    continue
                }
                val guItems = response.body.response!!.body!!.items!!.item
                for ((orgCd, orgdownNm, uprCd) in guItems!!) {
                    println("get guItem, orgCode : $orgCd, guItem.getOrgdownNm : $orgdownNm")

                    //gu 데이터에 이미 있으면 pass
                    if (guService.findByGuCode(orgCd!!) != null) continue
                    val guData = Gu()
                    guData.guCode = orgCd
                    guData.guName = orgdownNm
                    guData.orgCode = uprCd
                    guService.saveOrUpdate(guData)
                }
                println("finish!")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return true
    }
}