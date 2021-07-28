package com.sogong.sogong;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class SogongApplication {

    @Value("${spring.datasource.url}")
    private static String dataUrl;

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("현재시각 : "+new Date());
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SogongApplication.class, args);
        String str = ctx.getEnvironment().getProperty("spring.datasource.url");
        System.out.println(str);
    }


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setInterceptors(Lists.newArrayList(new RestTemplateLoggingRequestInterceptor(), new RestTemplateHeaderModifierInterceptor()));
//        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        return restTemplate;
    }

}
