package com.sogong.sogong.converter.animal;

import com.sogong.sogong.converter.AbstractDataConverter;
import com.sogong.sogong.entity.animal.AnimalDataEntity;
import com.sogong.sogong.entity.district.GuInfo;
import com.sogong.sogong.model.animal.AnimalData;
import com.sogong.sogong.model.animal.AnimalKind;
import com.sogong.sogong.services.animal.AnimalKindService;
//import org.apache.tomcat.util.http.fileupload.FileItem;
import com.sogong.sogong.services.district.GuService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.apache.commons.fileupload.disk.DiskFileItem.DiskFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MainAnimalListConverter extends AbstractDataConverter<AnimalData, AnimalDataEntity> {
    private final AnimalKindService animalKindService;
    private final GuService guService;

    public MainAnimalListConverter(AnimalKindService animalKindService, GuService guService) {
        this.animalKindService = animalKindService;
        this.guService = guService;
    }

    @Value("${animalImage.savePath}")
    private String savePath;

    @Override
    protected AnimalDataEntity createTarget() {return new AnimalDataEntity();}


    @Override
    public AnimalDataEntity convert(AnimalData source, AnimalDataEntity target) throws ConversionException, IOException {
        AnimalKind animalKind = animalKindService.findByTypeCode(source.getAnimalKindCode());

        if(animalKind == null){
            return null;
        }

        File file = new File(savePath + source.getId());
        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());

        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);

        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);


        target.setId(source.getId());
        target.setType(animalKind.getType());
        target.setImage(multipartFile);
        target.setKindName(source.getKindName());

        if(source.getNoticeEnd() != null) {
            Long dayDiff = ChronoUnit.DAYS.between(LocalDateTime.now(), source.getNoticeEnd());
            Integer dDay = 0;
            //당일이거나 이미 지난 경우엔 dDay 0으로 처리
            if (dayDiff > 0) {
                dDay = Math.toIntExact(dayDiff + 1);
            }
            target.setDDay("D-" + dDay);
        }

        GuInfo guInfo = guService.getGuInfo(source.getGuCode());


        target.setPlace(guInfo.getCity()+" "+guInfo.getGu());
        target.setDate(source.getHappenedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        String sex = "Q";

        switch(source.getSex()) {
            case "F" :
                sex = "여";
                break;
            case "M" :
                sex = "남";
                break;
            default :
                sex = "미상";
                break;
        }

        target.setSex(sex);
//        target.setFavorite();

        return target;
    }
}
