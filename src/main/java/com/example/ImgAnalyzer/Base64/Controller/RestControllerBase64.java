package com.example.ImgAnalyzer.Base64.Controller;

import com.example.ImgAnalyzer.Base64.Base64Decoder;
import com.example.ImgAnalyzer.Base64.Base64Encoder;
import com.example.ImgAnalyzer.Base64.Controller.DTO.DTOBase64;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import static com.example.ImgAnalyzer.Base64.Base64Encoder.cleanBase64;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/v1/img")
public class RestControllerBase64 {

    @PostMapping("/decode")
    public DTOBase64 decodeImageIntoBase64String(@RequestParam("file") MultipartFile file) throws Exception {
        // 1. Check if file is empty
        DTOBase64 dtoBase64 = new DTOBase64();
        Base64Decoder decoder = new Base64Decoder();
        if (file.isEmpty()) {
            dtoBase64.setBase64Value("Please select a file to upload.");
            return dtoBase64;
        }

        // 2. Validate that the file is actually an image
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            dtoBase64.setBase64Value("Only image files are allowed.");
            return dtoBase64;
        }

        // 3. Create target directory if it doesn't exist
        Path uploadPath = Paths.get("Images");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 4. Resolve clean file path and prevent directory traversal risks
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        Path filePath = uploadPath.resolve(fileName);

        // 5. Copy file stream to target storage location (Replace existing)
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


//        "Image uploaded successfully: " + fileName
        dtoBase64.setBase64Value(decoder.decode(new File("Images\\" + file.getOriginalFilename())));
        decoder = null;
        return dtoBase64;

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
