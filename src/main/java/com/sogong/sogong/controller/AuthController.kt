package com.animal.animal.controller

import com.sogong.sogong.services.auth.AuthenticationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth")
class AuthController(
        private val authenticationService: AuthenticationService
) {

    @GetMapping("/oauth")
    fun getAuthCode(@RequestParam("code") authorizationCode : String) : String {
//        authenticationService.getAccessToken(authorizationCode)
//        REST API KEY 532884bfb092b23c4e1eeb0c88a5124a
        return "g"
    }

}