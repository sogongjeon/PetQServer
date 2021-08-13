package com.sogong.sogong.services.auth

import com.sogong.sogong.entity.ResultEntity
import com.sogong.sogong.entity.auth.KakaoAuth

interface AuthenticationService {
    fun kakaoAuthorization(code : String) : ResultEntity<KakaoAuth>
}