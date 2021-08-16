package com.sogong.sogong;

import com.animal.animal.entity.external.FindAbandonedResponse;
import com.animal.animal.entity.external.PublicResponseItem;
import com.sogong.sogong.config.PublicAbandonedConfig;
import com.sogong.sogong.entity.animal.AnimalKindResponse;
import com.sogong.sogong.entity.animal.AnimalKindResponseItem;
import com.sogong.sogong.entity.animal.AnimalKindResponseItems;
import com.sogong.sogong.entity.external.GuResponse;
import com.sogong.sogong.entity.external.GuResponseItem;
import com.sogong.sogong.model.animal.AnimalKind;
import com.sogong.sogong.model.district.City;
import com.sogong.sogong.model.district.Gu;
import com.sogong.sogong.services.animal.AnimalKindService;
import com.sogong.sogong.services.district.CityService;
import com.sogong.sogong.services.district.GuService;
import com.sogong.sogong.type.animal.AnimalKindType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@ActiveProfiles("local")
class SogongApplicationTests {
    @Autowired
    CityService cityService;

    @Autowired
    GuService guService;

    @Autowired
    AnimalKindService animalKindService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PublicAbandonedConfig publicAbandonedConfig;

    @Test
    void contextLoads() {
        var cities = cityService.findAll();
        for (City c : cities) {
            System.out.println("cities : " + c.getCityName());
        }

    }

    @Test
    void updateGu() throws URISyntaxException {
        var cities = cityService.findAll();


        String url = publicAbandonedConfig.sigunguUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        for (City city : cities) {
            System.out.println("city OrgName :  " + city.getCityName());

            try {
                UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                        .queryParam("upr_cd", city.getCityCode())
                        .build(true);

                URI uri = new URI(builder.toUriString());

                HttpEntity<GuResponse> response = restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        new HttpEntity<String>(headers),
                        GuResponse.class);

                if (response.getBody().getResponse().getBody().getItems().toString().equals("") || response.getBody().getResponse().getBody() == null) {
                    System.out.println("no data, in " + city.getCityName());
                    continue;
                }
                List<GuResponseItem> guItems = response.getBody().getResponse().getBody().getItems().getItem();

                for (GuResponseItem guItem : guItems) {
                    System.out.println("get guItem, orgCode : " + guItem.getOrgCd() + ", guItem.getOrgdownNm : " + guItem.getOrgdownNm());

                    //gu 데이터에 이미 있으면 pass
                    if (guService.findByGuCode(guItem.getOrgCd()) != null) continue;

                    Gu guData = new Gu();
                    guData.setGuCode(guItem.getOrgCd());
                    guData.setGuName(guItem.getOrgdownNm());
                    guData.setCityCode(guItem.getUprCd());

                    guService.saveOrUpdate(guData);
                }
                System.out.println("finish!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //이게 찐이다(머신러닝용 제공 데이터)
    @Test
    void updateAnimalPhotoData2() throws URISyntaxException {
        List<AnimalKind> animalList = animalKindService.findAll();

        if (animalList != null) {
            for (AnimalKind animal : animalList) {
                String url = publicAbandonedConfig.animalDetailUrl;

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                        .queryParam("kind", animal.getKindCode())
                        .queryParam("numOfRows", 11565)
                        .build(true);

                URI uri = new URI(builder.toUriString());
                try {
                    HttpEntity<FindAbandonedResponse> response = restTemplate.exchange(
                            uri,
                            HttpMethod.GET,
                            new HttpEntity<FindAbandonedResponse>(headers),
                            FindAbandonedResponse.class);

                    List<PublicResponseItem> responseItems = response.getBody().getResponse().getBody().getItems().getItem();

                    for (PublicResponseItem item : responseItems) {
                        try {
                            Integer age = 2022 - Integer.parseInt(item.getAge().replaceAll("[^0-9]", ""));

                            String pattern = "(?=\\[).*(?<=\\])";

                            String kindName = item.getKindCd().replaceAll(pattern, "").trim();

                            String IMAGE_URL = item.getPopfile();

                            URL imgUrl = new URL(IMAGE_URL);
                            String extension = IMAGE_URL.substring(IMAGE_URL.lastIndexOf('.') + 1);

                            if (kindName == null || kindName.equals("")) {
                                continue;
                            }

                            BufferedImage image = ImageIO.read(imgUrl);
                            File file = new File("/Users/dajung/dev/sogong/src/main/resources/img2/" + animal.getKindName() + "/" + kindName + "_" + age + ".jpg");

                            if (!file.exists()) {
                                //디렉토리 생성 메서드
                                file.mkdirs();
                                System.out.println("created directory successfully!");
                            }

                            ImageIO.write(image, extension, file);


                        } catch(Exception e){
                            e.printStackTrace();
                            System.out.println("Sorry, during update Animal Photo data, " + e);
                        }
                    }
                } catch(RestClientException e) {
                    System.out.println("JSON parse error! during parsing"+animal.getKindName());
                    continue;
                }

            }


        }

    }

    @Test
    void orgNmTest() {
        String orgNm = "제주특별자치도 제주시 첨단동길 184-14 (용강동)";

        String word1 = orgNm.split("\\s")[0];
        String word2 = orgNm.split("\\s")[1];

        System.out.println("word1 : "+word1);
        System.out.println("word2 L "+word2 );


//        int idx = orgNm.indexOf(" ");
//
//        // @ 앞부분을 추출
//        // substring은 첫번째 지정한 인덱스는 포함하지 않는다.
//        // 아래의 경우는 첫번째 문자열인 a 부터 추출된다.
//        String city = orgNm.substring(0, idx);
//
//        // 뒷부분을 추출
//        // 아래 substring은 @ 바로 뒷부분인 n부터 추출된다.
//        String gu = orgNm.substring(idx+1);
//
//        Gu guData = guService.findByGuName(city, gu);
//
//
//        System.out.println("시 : "+city+",구 : "+gu+",구 코드 : "+guData.getGuCode()+"지역 코드 : "+guData.getCityCode());
    }

    @Test
    void localDateParseTest() throws ParseException {
        String testDateTime = "20210521";

//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
//
//        LocalDateTime.parse(testDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//        System.out.println(LocalDateTime.parse(testDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

//        LocalDate date = LocalDate.parse(testDateTime, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDateTime a = LocalDate.parse(testDateTime, DateTimeFormatter.ofPattern("yyyyMMdd")).atTime(0,0,0);
        System.out.println(a);


    }

    @Test
    void getOnlyNumbrerTest() throws ParseException {
        String testDateTime = "2019(년생)";

        Integer a = Integer.parseInt(testDateTime.replaceAll("[^0-9]",""));

        System.out.println("a");


    }

    @Test
    void enumTest() {
        String name = "DOG";

        AnimalKindType a = AnimalKindType.valueOf(name);
        System.out.println(a.getKey());
    }
}
