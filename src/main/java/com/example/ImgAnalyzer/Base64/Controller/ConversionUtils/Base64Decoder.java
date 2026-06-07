package com.example.ImgAnalyzer.Base64;

import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Bean
public final class Base64Decoder extends Base64Converter {

    @Override
    public String decode(File ...file) throws Exception {
//        String imagePath = "path/to/your/image.png";
        byte[] imageBytes = Files.readAllBytes(Paths.get(file[0].getAbsolutePath()));
//                Files.readAllBytes(Paths.get(imagePath));

        // Encode bytes to Base64 string
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @Override
    public void encode(String ...outputPath) throws Exception {
        throw new RuntimeException("You cannot decode using this context.");
    }
}
