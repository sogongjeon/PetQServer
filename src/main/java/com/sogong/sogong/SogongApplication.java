package com.sogong.sogong;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SogongApplication {

    public static void main(String[] args) {
        SpringApplication.run(SogongApplication.class, args);
    }

//    @Bean
//    public Gson gson() {
//        return new Gson();
//    }
//
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setInterceptors(Lists.newArrayList(new RestTemplateLoggingRequestInterceptor(), new RestTemplateHeaderModifierInterceptor()));
//        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        return restTemplate;
    }

}
