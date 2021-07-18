package com.sogong.sogong;

import com.sogong.sogong.config.PublicAbandonedConfig;
import com.sogong.sogong.entity.external.GuResponse;
import com.sogong.sogong.entity.external.GuResponseItem;
import com.sogong.sogong.model.district.City;
import com.sogong.sogong.model.district.Gu;
import com.sogong.sogong.services.district.CityService;
import com.sogong.sogong.services.district.GuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootTest
class SogongApplicationTests {
    @Autowired
    CityService cityService;

    @Autowired
    GuService guService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PublicAbandonedConfig publicAbandonedConfig;

    @Test
    void contextLoads() {
        var cities = cityService.findAll();
        for (City c :cities){
            System.out.println("cities : "+c.getOrgName());
        }

    }

    @Test
    void updateGu() throws URISyntaxException {
        var cities = cityService.findAll();


        String url = publicAbandonedConfig.sigunguUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (City city :cities){
            System.out.println("city OrgName :  " + city.getOrgName());

            try {
                UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                        .queryParam("upr_cd", city.getOrgCode())
                        .build(true);

                URI uri = new URI(builder.toUriString());

                HttpEntity<GuResponse> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        new HttpEntity<String>(headers),
                        GuResponse.class);

                if(response.getBody().getResponse().getBody().getItems().toString().equals("") || response.getBody().getResponse().getBody() == null) {
                    System.out.println("no data, in "+city.getOrgName());
                    continue;
                }
                List<GuResponseItem> guItems = response.getBody().getResponse().getBody().getItems().getItem();

                for(GuResponseItem guItem : guItems) {
                    System.out.println("get guItem, orgCode : "+guItem.getOrgCd()+", guItem.getOrgdownNm : "+guItem.getOrgdownNm());

                    //gu 데이터에 이미 있으면 pass
                    if(guService.findByGuCode(guItem.getOrgCd()) != null) continue;

                    Gu guData = new Gu();
                    guData.setGuCode(guItem.getOrgCd());
                    guData.setGuName(guItem.getOrgdownNm());
                    guData.setOrgCode(guItem.getUprCd());

                    guService.saveOrUpdate(guData);
                }
                System.out.println("finish!" );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
