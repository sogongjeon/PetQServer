package com.sogong.sogong.controller.external

import com.animal.animal.entity.external.FindAbandonedResponse
import com.google.gson.Gson
import com.sogong.sogong.config.PublicAbandonedConfig
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder.encode
import java.nio.charset.Charset
import java.util.*


@Service
class PublicAbandonedServiceImpl(
        private val restTemplate: RestTemplate,
        private val publicAbandonedConfig: PublicAbandonedConfig,
        private val gson: Gson
) : PublicAbandonedService {

    override fun findAnimalList(): FindAbandonedResponse? {
//        println(publicAbandonedConfig.animalDetailUrl)

        var url = publicAbandonedConfig.animalDetailUrl

        val headers = HttpHeaders()

        headers.contentType = MediaType.APPLICATION_JSON

        try {

            var builder = UriComponentsBuilder.fromHttpUrl(url.toString())
                    .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                    .queryParam("bgnde", "20210701")
                    .queryParam("endde", "20210720")
                    .queryParam("upr_cd", "6110000")
                    .queryParam("org_cd", "3090000")
                    .build(true)

            val uri = URI(builder.toUriString())

            val response: HttpEntity<FindAbandonedResponse> = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    HttpEntity<String>(headers),
                    FindAbandonedResponse::class.java)

            println("url:${builder.toUriString()}")
            println("message: ${response.body}")

        } catch (e : Exception) {
            println("Exception, during get nongchuk api, $e")
        }

        return null
    }
}