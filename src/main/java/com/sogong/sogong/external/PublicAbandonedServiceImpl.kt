package com.sogong.sogong.controller.external

import com.animal.animal.entity.external.FindAbandonedResponse
import com.sogong.sogong.config.PublicAbandonedConfig
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PublicAbandonedServiceImpl(
        private val restTemplate: RestTemplate,
        private val publicAbandonedConfig: PublicAbandonedConfig,
        private val gson: Gson
) : PublicAbandonedService {

    override fun findAnimalList(): FindAbandonedResponse? {
//        http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?bgnde=20210701&endde=20210720&pageNo=1&numOfRows=100&ServiceKey=HPNkCS77rHAUBC89Rx10y%2BUHLv3hx7usn%2FPdyT7%2BgtVxdD5uh1gfoQU9yhU3f%2B2iXMTcNDNDCFBnClctSLEgmQ%3D%3D&upr_cd=6110000&org_cd=3090000
        println(publicAbandonedConfig.animalDetailUrl)

        var params = HashMap<String, Any>()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        params["bgnde"] = "20210701"
        params["endde"] = "20210720"
        params["ServiceKey"] = "HPNkCS77rHAUBC89Rx10y%2BUHLv3hx7usn%2FPdyT7%2BgtVxdD5uh1gfoQU9yhU3f%2B2iXMTcNDNDCFBnClctSLEgmQ%3D%3D"
        params["upr_cd"] = "6110000"
        params["org_cd"] = "3090000"

        val request = HttpEntity(params, headers)

        val response = restTemplate.postForEntity("${publicAbandonedConfig.animalDetailUrl}", request, FindAbandonedResponse::class.java)

        println(response)


        return null
    }
}