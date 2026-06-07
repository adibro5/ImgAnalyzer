package com.example.ImgAnalyzer.Base64.Controller;

import com.example.ImgAnalyzer.Base64.Controller.ConversionUtils.Base64Encoder;
import com.example.ImgAnalyzer.Base64.Controller.DTO.DTOBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/v1/img")
public class RestControllerBase64 {

    Base64Services bServe;

    @Autowired
    RestControllerBase64(Base64Services bServe) {
        this.bServe = bServe;
    }



    @PostMapping("/decode")
    public DTOBase64 decodeImageIntoBase64String(@RequestParam("files") MultipartFile[] file) throws Exception {
        // 1. Check if file is empty
        DTOBase64 dtoBase64 = new DTOBase64();
//        dtoBase64.setBase64Value();

        return bServe.convertImageToBase64(file);

    }

    @PostMapping("/encode")
    public String encodeBase64StringIntoImage(@RequestBody Map<String, String> fileString) throws Exception {

        Base64Encoder encoder = new Base64Encoder();
        String base64ImageString = fileString.get("base64Value");

        // Example with a nested folder path that might not exist yet
        String outputImagePath = fileString.get("destinationFolder");
//        String cleanBase64 = ;

        encoder.encode(encoder.cleanBase64(base64ImageString), outputImagePath);

        System.out.println("Image saved successfully to: " + outputImagePath);
        encoder = null;
        return "success";

    }



}
