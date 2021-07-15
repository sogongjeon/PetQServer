package com.sogong.sogong.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "public.nongchuk")
class PublicAbandonedConfig {
    //서비스키(농축부)
    lateinit var serviceKey: String

    //URL
    lateinit var sidoUrl: String
    lateinit var sigunguUrl: String
    lateinit var shelterUrl: String
    lateinit var kindUrl: String
    lateinit var animalDetailUrl: String

}