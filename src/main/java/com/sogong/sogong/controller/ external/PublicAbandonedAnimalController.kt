package com.sogong.sogong.controller.` external`

//import com.animal.animal.services.external.PublicAbandonedService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sogong.sogong.config.PublicAbandonedConfig
import com.sogong.sogong.controller.external.PublicAbandonedService
import com.sogong.sogong.entity.animal.AnimalKindResponse
import com.sogong.sogong.entity.external.GuResponse
import com.sogong.sogong.model.animal.Animal
import com.sogong.sogong.model.district.City
import com.sogong.sogong.model.district.Gu
import com.sogong.sogong.services.animal.AnimalService
import com.sogong.sogong.services.district.CityService
import com.sogong.sogong.services.district.GuService
import org.apache.tomcat.util.json.JSONParser
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/v1/external/public/abandoned")
class PublicAbandonedAnimalController(
        private val publicAbandonedService: PublicAbandonedService,
        private val guService: GuService,
        private val cityService: CityService,
        private val animalService: AnimalService,
        private val publicAbandonedConfig: PublicAbandonedConfig,
        private val restTemplate: RestTemplate
) {

    @Value("\${public.nongchuk.dogCode}")
    private val dogCode: String? = null

    @Value("\${public.nongchuk.catCode}")
    private val catCode: String? = null

    @Value("\${public.nongchuk.etcCode}")
    private val etcCode: String? = null


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
                println("finish updating "+city.orgName+"Gu DATA!")
            } catch (e: Exception) {
                e.printStackTrace()
                println("failed to get "+city.orgName +"data, Exception : " +e)
            }
        }
        return true
    }

    @PostMapping("/update-animal-type")
    fun updateAnimalType() : Boolean {
        var url = publicAbandonedConfig.kindUrl

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_XML
        //개
        try {
            var builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                    .queryParam("up_kind_cd", dogCode)
                    .build(true)
            var uri = URI(builder.toUriString())
            val response: HttpEntity<String> = restTemplate.exchange<String>(
                    uri,
                    HttpMethod.GET,
                    HttpEntity<String>(headers),
                    String::class.java)

            val gson = Gson()
            val arrayTutorialType = object : TypeToken<AnimalKindResponse>() {}.type

            var responseData : AnimalKindResponse = gson.fromJson(response.body, arrayTutorialType)

            var kindItems = responseData.response!!.body!!.items!!.item

            if (kindItems != null) {
                for (kindItem in kindItems) {
                    println("get dog kind...")

                    //데이터에 이미 있는 축종코드면 pass
                    if (animalService.findByKindCode(kindItem.kindCd!!) != null) continue

                    val animalData = Animal()
                    animalData.animal = "DOG"
                    animalData.kindCode = kindItem.kindCd
                    animalData.kindName = kindItem.KNm

                    animalService.saveOrUpdate(animalData)
                }
            }
            println("finished updating DOG data!")
            println("Total DOG type data : ${kindItems!!.size}")
        } catch (e: Exception) {
            e.printStackTrace()
            println("failed to get DOG data, Exception : $e")
        }

        //고양이
        try {
            var builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                    .queryParam("up_kind_cd", catCode)
                    .build(true)
            var uri = URI(builder.toUriString())
            val response: HttpEntity<String> = restTemplate.exchange<String>(
                    uri,
                    HttpMethod.GET,
                    HttpEntity<String>(headers),
                    String::class.java)

            val gson = Gson()
            val arrayTutorialType = object : TypeToken<AnimalKindResponse>() {}.type

            var responseData : AnimalKindResponse = gson.fromJson(response.body, arrayTutorialType)

            var kindItems = responseData.response!!.body!!.items!!.item

            if (kindItems != null) {
                for (kindItem in kindItems) {
                    println("get cat kind...")

                    //데이터에 이미 있는 축종코드면 pass
                    if (animalService.findByKindCode(kindItem.kindCd!!) != null) continue

                    val animalData = Animal()
                    animalData.animal = "CAT"
                    animalData.kindCode = kindItem.kindCd
                    animalData.kindName = kindItem.KNm

                    animalService.saveOrUpdate(animalData)
                }
            }
            println("finished updating CAT data!")
            println("Total CAT type data : ${kindItems!!.size}")
        } catch (e: Exception) {
            e.printStackTrace()
            println("failed to get CAT data, Exception : $e")
        }

        //기타축종(아직 세분화가 되어있지 않아 주석처리)
//        try {
//            var builder = UriComponentsBuilder.fromHttpUrl(url)
//                    .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
//                    .queryParam("up_kind_cd", etcCode)
//                    .build(true)
//            var uri = URI(builder.toUriString())
//            val response: HttpEntity<String> = restTemplate.exchange<String>(
//                    uri,
//                    HttpMethod.GET,
//                    HttpEntity<String>(headers),
//                    String::class.java)
//
//            val gson = Gson()
//            val arrayTutorialType = object : TypeToken<AnimalKindResponse>() {}.type
//
//            var responseData : AnimalKindResponse = gson.fromJson(response.body, arrayTutorialType)
//
//            var kindItems = responseData.response!!.body!!.items!!.item
//
//            if (kindItems != null) {
//                for (kindItem in kindItems) {
//                    println("get etc kind...")
//
//                    //데이터에 이미 있는 축종코드면 pass
//                    if (animalService.findByKindCode(kindItem.kindCd!!) != null) continue
//
//                    val animalData = Animal()
//                    animalData.animal = "ETC"
//                    animalData.kindCode = kindItem.kindCd
//                    animalData.kindName = kindItem.KNm
//
//                    animalService.saveOrUpdate(animalData)
//                }
//            }
//            println("finish updating ETC data!")
//            println("Total ETC type data : ${kindItems!!.size}")
//        } catch (e: Exception) {
//            e.printStackTrace()
//            println("failed to get ETC data, Exception : $e")
//        }

        return true
    }
}