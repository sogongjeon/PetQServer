package com.sogong.sogong.controller.animal;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/v1/image")
public class ImageController{
    @Value("${animalImage.savePath}")
    String savePath = "";

    @Value("${profileImage.savePath}")
    String profilePath = "";

    @Value("${animalImage.lostAnimalSavePath}")
    String lostPetPath ="";

    ServletContext servletContext;

    public ImageController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable String id) throws IOException {
        InputStream imageStream = new FileInputStream(savePath+id+".jpg");
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return imageByteArray;
    }

    @GetMapping(
            value = "/lostpet/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getLostPetImageWithMediaType(@PathVariable String id) throws IOException {
        InputStream imageStream = new FileInputStream(lostPetPath+id+".jpg");
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return imageByteArray;
    }

    @GetMapping(
            value = "/profile/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )

    public @ResponseBody byte[] getProfileImage(@PathVariable String id) throws IOException {
        InputStream imageStream = new FileInputStream(profilePath+id+".jpg");
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return imageByteArray;
    }



}
