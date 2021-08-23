package com.sogong.sogong.controller.animal;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/v1/image")
public class ImageController{
    @Value("${animalImage.savePath}")
    String savePath = "";

    ServletContext servletContext;

    public ImageController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping(
            value = "{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable String id) throws IOException {
        String a = savePath+id+".jpg";
        InputStream in = getClass().getResourceAsStream(a);
        return IOUtils.toByteArray(in);
    }


}
