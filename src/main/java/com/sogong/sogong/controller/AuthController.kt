package com.animal.animal.controller

import com.sogong.sogong.services.auth.AuthenticationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth")
class AuthController(
        private val authenticationService: AuthenticationService
) {

    @GetMapping("/oauth")
    fun getAuthCode(@RequestParam("code") code : String) : String {
//      (1) 카카오로그인
        var kakaoAuth = authenticationService.kakaoAuthorization(code)
        return "g"
    }

}