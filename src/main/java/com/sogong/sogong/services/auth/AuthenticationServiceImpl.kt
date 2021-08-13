package com.sogong.sogong.services.auth

import com.google.gson.Gson
import com.sogong.sogong.controller.external.PublicAbandonedAnimalController
import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.auth.KakaoAuth
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
open class AuthenticationServiceImpl(
    private val restTemplate: RestTemplate,
    private val gson: Gson
) : AuthenticationService {

    private val log: Logger = LoggerFactory.getLogger(PublicAbandonedAnimalController::class.java)

    @Value("\${kakao.auth.id}")
    private val kakaoClientId : String ?= null

    @Value("\${kakao.auth.id}")
    private val kakaoRedirectUri : String ?= null


    override fun kakaoAuthorization(code : String) : ResultEntity<KakaoAuth> {
        val grantType = "authorization_code" //고정

        var headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val kakaoAuth = KakaoAuth()

        try {
            val params = LinkedMultiValueMap<String, String>()
            params.add("grant_type", grantType)
            params.add("client_id", kakaoClientId)
            params.add("redirect_uri", kakaoRedirectUri)
            params.add("code", code) //프론트엔드로부터 받은 인가코드

            val request = HttpEntity<MultiValueMap<String, String>>(params, headers)
            val url = "https://kauth.kakao.com/oauth/token"
            val response = restTemplate.postForEntity(url, request, String::class.java)
            if (response.statusCode == HttpStatus.OK) {
                val result = gson.toJson(response.body, kakaoAuth::class.java)
                return ResultEntity<KakaoAuth>(gson.fromJson(response.body, kakaoAuth::class.java))
            } else {
                return ResultEntity("400", "카카오로부터 토큰을 받아오는데 실패하였습니다.")
            }
        } catch (e: Exception) {
            log.error("Exception, during get kakao authorization, message=${e.message}", e)
            return ResultEntity("404", "에러발생")
        }

    }

}