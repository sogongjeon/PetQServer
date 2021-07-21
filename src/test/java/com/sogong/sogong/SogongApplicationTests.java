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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
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

    @Test
    void updateAnimalPhotoData() {

        String url = publicAbandonedConfig.animalDetailUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("ServiceKey", publicAbandonedConfig.serviceKey)
                    .queryParam("numOfRows", 11565)
                    .build(true);

            URI uri = new URI(builder.toUriString());

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
                    File file = new File("/Users/dajung/dev/sogong/src/main/resources/img/" + kindName + "/" + kindName + "_" + age + ".jpg");

                    if (!file.exists()) {
                        //디렉토리 생성 메서드
                        file.mkdirs();
                        System.out.println("created directory successfully!");
                    }

                    ImageIO.write(image, extension, file);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("get error : " + item.getDesertionNo());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Sorry, during update Animal Photo data, " + e);
        }
    }

    @Test
    void regularExTest() throws IOException {
        String kindSample = "[개] 한국 고양이";
        String ageSample = "2020년생";

        String IMAGE_URL1 = "http://www.animal.go.kr/files/shelter/2021/06/202107011307183.jpg";
        String IMAGE_URL2 = "http://www.animal.go.kr/files/shelter/2021/06/202106302106343.png";

//        String extension3 = IMAGE_URL1.substring(IMAGE_URL1.lastIndexOf('.') + 1);
//        String extension4 = IMAGE_URL2.substring(IMAGE_URL2.lastIndexOf('.') + 1);

        String pattern = "(?=\\[).*(?<=\\])";

        String kindName = kindSample.replaceAll(pattern, "").trim();
        Integer age = 2023 - Integer.parseInt(ageSample.replaceAll("[^0-9]", ""));
        System.out.println(kindName + age);

        URL url = new URL(IMAGE_URL2);
        String extension = IMAGE_URL2.substring(IMAGE_URL2.lastIndexOf('.') + 1);
//        String extension2 = IMAGE_URL2.substring(IMAGE_URL2.indexOf('.') + 1);

        BufferedImage image = ImageIO.read(url);
        File file = new File("/Users/dajung/dev/sogong/src/main/resources/test/" + kindName + "/" + kindName + "_" + age + ".jpg");

        if (!file.exists()) {
            //디렉토리 생성 메서드
            file.mkdirs();
            System.out.println("created directory successfully!");
        }

//        System.out.println("extension3 : "+extension3);
//        System.out.println("extension4 : "+extension4);
        ImageIO.write(image, extension, file);

//   /Users/dajung/dev/sogong/src/main/resources/static/img

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
}
