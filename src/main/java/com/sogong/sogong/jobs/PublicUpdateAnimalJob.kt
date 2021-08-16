package com.sogong.sogong.jobs

import com.animal.animal.entity.external.FindAbandonedResponse
import com.sogong.sogong.config.PublicAbandonedConfig
import com.sogong.sogong.controller.external.PublicAbandonedAnimalController
import com.sogong.sogong.model.animal.AnimalData
import com.sogong.sogong.model.district.Gu
import com.sogong.sogong.services.animal.AnimalDataService
import com.sogong.sogong.services.animal.AnimalKindService
import com.sogong.sogong.services.district.CityService
import com.sogong.sogong.services.district.GuService
import com.sogong.sogong.type.animal.ProtectType
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

@Component
class PublicUpdateAnimalJob (
        private val animalKindService: AnimalKindService,
        private val publicAbandonedConfig: PublicAbandonedConfig,
        private val restTemplate: RestTemplate,
        private val animalDataService: AnimalDataService,
        private val guService: GuService,
        private val cityService: CityService
) {
    private val log: Logger = LoggerFactory.getLogger(PublicUpdateAnimalJob::class.java)

    @Value("\${animalImage.savePath}")
    private val savePath : String ?= null

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
                                    newAnimalData.protectType = ProtectType.SHELTER

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
}
