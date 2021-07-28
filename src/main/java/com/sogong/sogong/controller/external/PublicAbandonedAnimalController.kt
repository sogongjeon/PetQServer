package com.sogong.sogong.controller.external

//import com.animal.animal.services.external.PublicAbandonedService
import com.animal.animal.entity.external.FindAbandonedResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sogong.sogong.config.PublicAbandonedConfig
import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.animal.AnimalKindResponse
import com.sogong.sogong.entity.animal.AnimalKindResponseItem
import com.sogong.sogong.entity.external.GuResponse
import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.model.animal.AnimalKind
import com.sogong.sogong.model.district.City
import com.sogong.sogong.model.district.Gu
import com.sogong.sogong.services.animal.AnimalDataService
import com.sogong.sogong.services.animal.AnimalKindService
import com.sogong.sogong.services.district.CityService
import com.sogong.sogong.services.district.GuService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.awt.image.BufferedImage
import java.io.File
import java.net.URI
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO

@RestController
@RequestMapping("/v1/external/public/abandoned")
class PublicAbandonedAnimalController(
        private val publicAbandonedService: PublicAbandonedService,
        private val guService: GuService,
        private val cityService: CityService,
        private val animalKindService: AnimalKindService,
        private val animalDataService: AnimalDataService,
        private val publicAbandonedConfig: PublicAbandonedConfig,
        private val restTemplate: RestTemplate
) {
    private val log: Logger = LoggerFactory.getLogger(PublicAbandonedAnimalController::class.java)

    @Value("\${public.nongchuk.dogCode}")
    private val dogCode: String? = null

    @Value("\${public.nongchuk.catCode}")
    private val catCode: String? = null

    @Value("\${public.nongchuk.etcCode}")
    private val etcCode: String? = null

    @Value("\${animalImage.savePath}")
    private val savePath : String ?= null



    @PostMapping("/find")
    fun findAnimal(): String {
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
            log.info("city OrgName :  " + city.cityName)
            try {
                val builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                        .queryParam("upr_cd", city.cityCode)
                        .build(true)
                val uri = URI(builder.toUriString())
                try {
                    val response: HttpEntity<GuResponse> = restTemplate.exchange<GuResponse>(
                            uri,
                            HttpMethod.GET,
                            HttpEntity<String>(headers),
                            GuResponse::class.java)


                    val guItems = response.body!!.response!!.body!!.items!!.item
                    for ((orgCd, orgdownNm, uprCd) in guItems!!) {
                        log.info("get guItem, orgCode : $orgCd, guItem.getOrgdownNm : $orgdownNm")

                        //gu 데이터에 이미 있으면 pass
                        if (guService.findByGuCode(orgCd!!) != null) continue
                        val guData = Gu()
                        guData.guCode = orgCd
                        guData.guName = orgdownNm
                        guData.cityCode = uprCd
                        guService.saveOrUpdate(guData)
                    }
                    log.info("finish updating " + city.cityName + "Gu DATA!")
                } catch(e : RestClientException) {
                    log.error("no data in ${city.cityName}, continue")
                    continue;
                }
            } catch (e: Exception) {
                e.printStackTrace()
                log.error("failed to get "+city.cityName +"data, Exception : " +e)
                continue;
            }
        }
        log.info("finished to update GU data!")
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
                    log.info("get dog kind...")

                    //데이터에 이미 있는 축종코드면 pass
                    if (animalKindService.findByTypeCode(kindItem.kindCd!!) != null) continue

                    val animalData = AnimalKind()
                    animalData.type = "DOG"
                    animalData.kindCode = kindItem.kindCd
                    animalData.kindName = kindItem.KNm

                    animalKindService.saveOrUpdate(animalData)
                }
            }
            log.info("finished updating DOG data!")
            log.info("Total DOG type data : ${kindItems!!.size}")
        } catch (e: Exception) {
            e.printStackTrace()
            log.info("failed to get DOG data, Exception : $e")
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
                    log.info("get cat kind...")

                    //데이터에 이미 있는 축종코드면 pass
                    if (animalKindService.findByTypeCode(kindItem.kindCd!!) != null) continue

                    val animalData = AnimalKind()
                    animalData.type = "CAT"
                    animalData.kindCode = kindItem.kindCd
                    animalData.kindName = kindItem.KNm

                    animalKindService.saveOrUpdate(animalData)
                }
            }
            log.info("finished updating CAT data!")
            log.info("Total CAT type data : ${kindItems!!.size}")
        } catch (e: Exception) {
            e.printStackTrace()
            log.info("failed to get CAT data, Exception : $e")
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
//                    log.info("get etc kind...")
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
//            log.info("finish updating ETC data!")
//            log.info("Total ETC type data : ${kindItems!!.size}")
//        } catch (e: Exception) {
//            e.printStackTrace()
//            log.info("failed to get ETC data, Exception : $e")
//        }

        return true
    }

    @Scheduled(cron = "\${job.updateAnimal.cron}")
    fun updateAnimal() : Boolean {
        var animalList = animalKindService.findAll();
        log.info("# =======================")
        log.info("Start updating animal data from nongchukbu open api")

        if(animalList != null) {
            for(animalKind in animalList) {
                val url = publicAbandonedConfig.animalDetailUrl;

                val headers = HttpHeaders()
                headers.contentType = MediaType.APPLICATION_JSON

                var builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                        .queryParam("kind", animalKind.kindCode)
                        .queryParam("numOfRows", 11000)
                        .build(true)

                var uri = URI(builder.toUriString())

                try {
                    var response: HttpEntity<FindAbandonedResponse> = restTemplate.exchange<FindAbandonedResponse>(
                            uri,
                            HttpMethod.GET,
                            HttpEntity<FindAbandonedResponse>(headers),
                            FindAbandonedResponse::class.java
                    )

                    var responseItems = response.body!!.response!!.body!!.items!!.item

                    if (responseItems != null) {
                        for(item in responseItems) {
                            try {
                                var animalData = animalDataService.findByDesertionNo(item.desertionNo!!)

                                if(animalData != null) {
                                    //1. 기존 DB에 있는 동물일때
                                        //1-1. 해당 동물이 '종료' 상태일때 DB 수정없이 바로 continue
                                    if(animalData.processState.contains("종료")) {
                                        continue;
                                    } else {
                                        //1-2. 종료 상태가 아닐시 상태값만 update
                                        animalData.processState = item.processState
                                        animalDataService.saveOrUpdate(animalData)
                                        log.info("update additional animal data, desertionNo : ${item.desertionNo}")
                                    }

                                } else {
                                    //2. 기존 DB에 없던 동물일 경우
                                    var newAnimalData = AnimalData()

                                    var address = item.careAddr!!

                                    var cityName = address.split(" ")[0]
                                    var guName = address.split(" ")[1]

                                    var guData = guService.findByGuName(cityName, guName)

                                    //기존에 없던 구 데이터라면 추가해주기
                                    if(guData == null) {
                                        var newGu = Gu()
                                        newGu.cityCode = cityService.findByCityName(cityName)!!.cityCode
                                        newGu.guCode = (Integer.parseInt(guService.getLastGuCode()) + 1).toString()
                                        newGu.guName = guName

                                        guService.saveOrUpdate(newGu)

                                        guData = newGu
                                    }

                                    newAnimalData.guCode = guData!!.guCode
                                    newAnimalData.animalKindCode = animalKind.kindCode
                                    newAnimalData.desertionNo = item.desertionNo
                                    newAnimalData.happenedDate = LocalDate.parse(item.happenDt, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0,0,0)
                                    newAnimalData.happenedPlace = item.happenPlace
                                    newAnimalData.kindName = item.kindCd
                                    newAnimalData.color = item.colorCd
                                    newAnimalData.birthYear = Integer.parseInt(item.age!!.replace("[^0-9]".toRegex(), ""))
                                    newAnimalData.weight = item.weight
                                    newAnimalData.noticeNo = item.noticeNo
                                    newAnimalData.noticeStart = LocalDate.parse(item.noticeSdt, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0,0,0)
                                    newAnimalData.noticeEnd= LocalDate.parse(item.noticeSdt, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0,0,0)
                                    newAnimalData.imageUrl = item.popfile
                                    newAnimalData.processState = item.processState
                                    newAnimalData.sex = item.sexCd
                                    newAnimalData.isNeutered = item.neuterYn
                                    newAnimalData.specialMark = item.specialMark
                                    newAnimalData.careName = item.careNm
                                    newAnimalData.careTel = item.careTel
                                    newAnimalData.careAddress = item.careAddr
                                    newAnimalData.managerName = item.chargeNm
                                    newAnimalData.managerTel = item.officetel

                                    animalDataService.saveOrUpdate(newAnimalData)

                                    var IMAGE_URL = item.popfile
                                    var imgUrl = URL(IMAGE_URL)
                                    val extension = IMAGE_URL!!.substring(IMAGE_URL.lastIndexOf('.') + 1)


                                    val image: BufferedImage = ImageIO.read(imgUrl)
                                    val file = File( savePath + item.desertionNo + ".jpg")

                                    if (!file.exists()) {
                                        //디렉토리 생성 메서드
                                        file.mkdirs()
                                    }

                                    ImageIO.write(image, extension, file)
                                    log.info("insert new animal data, desertionNo : "+item.desertionNo)

                                }

                            } catch(e : Exception){
                                e.printStackTrace();
                                log.error("Sorry, during update Animal Data from public api,(desertionNo : "+item.desertionNo+"), " + e );
                            }

                        }
                    }

                } catch(e : RestClientException) {
                    log.error("no data, "+animalKind.kindName);
                    continue;
                }
            }
        }
        return true;
    }

    @PostMapping("/animal-kind")
    fun testData() : ResultEntity<AnimalKindResponseItem> {
        var response = AnimalKindResponseItem()

        response.KNm = "골든 리트리버"
        response.kindCd = "000054"

        return ResultEntity(response)
    }


}