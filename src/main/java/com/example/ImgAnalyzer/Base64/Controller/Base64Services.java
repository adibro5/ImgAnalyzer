package com.example.ImgAnalyzer.Base64.Controller;

import com.example.ImgAnalyzer.Base64.Controller.ConversionUtils.Base64Decoder;
import com.example.ImgAnalyzer.Base64.Controller.ConversionUtils.Base64Encoder;
import com.example.ImgAnalyzer.Base64.Controller.DTO.DTOBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;

@Service
public class Base64Services {

    DTOBase64 dtoBase64;
    Base64Decoder decoder;
    Base64Encoder encoder;


    @Autowired
    Base64Services(Base64Decoder decoder, DTOBase64 dtoBase64, Base64Encoder encoder) {
        this.decoder = decoder;
        this.dtoBase64 = dtoBase64;
        this.encoder = encoder;
    }

    public DTOBase64 convertImageToBase64(MultipartFile ...file) throws Exception {


        nullCheck(file);

        typeCheck(file);

        copyStreamDirs(file);

        actualDecode(file);

        deleteObjects(decoder);

        return dtoBase64;
    }

    private DTOBase64 nullCheck(MultipartFile[] file) {
        dtoBase64.setBase64Value("passed");
        if (file == null || file[0] == null || file[0].isEmpty()) {
            dtoBase64.setBase64Value("Please select a file to upload.");
            return dtoBase64;
        }
        return dtoBase64;
    }

    private DTOBase64 typeCheck(MultipartFile[] file) {
        // 2. Validate that the file is actually an image
        String contentType = file[0].getContentType();
        dtoBase64.setBase64Value("passed");
        if (contentType == null || !contentType.startsWith("image/")) {
            dtoBase64.setBase64Value("Only image files are allowed.");
            return dtoBase64;
        }
        return dtoBase64;
    }

    private Path createTargetDirIfNotExists() throws IOException {
        // 3. Create target directory if it doesn't exist
        Path uploadPath = Paths.get("Images");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        return uploadPath;
    }

    private Path dirSetup(MultipartFile ...file) throws IOException {
        // 4. Resolve clean file path and prevent directory traversal risks
        String fileName = Objects.requireNonNull(file[0].getOriginalFilename());
        Path filePath = createTargetDirIfNotExists().resolve(fileName);
        return filePath;
    }


    private void copyStreamDirs(MultipartFile ...file) throws IOException {
        // 5. Copy file stream to target storage location (Replace existing)
        Files.copy(file[0].getInputStream(), dirSetup(file), StandardCopyOption.REPLACE_EXISTING);
    }

    private void deleteObjects(Base64Decoder decoder) {
        decoder = null;
    }

    private void actualDecode(MultipartFile ...file) throws Exception {
        dtoBase64.setBase64Value(decoder.decode(new File("Images\\" + file[0].getOriginalFilename())));
    }

    private void convertBase64StringToImage(String base64String, String outputPath) throws Exception {
        encoder.encode(encoder.cleanBase64(base64String), outputPath);
    }


    public void createImageFromBase64String(Map<String, String> fileString) throws Exception {

        String base64ImageString = fileString.get("base64Value");

        // Example with a nested folder path that might not exist yet
        String outputImagePath = fileString.get("destinationFolder");

        convertBase64StringToImage(base64ImageString, outputImagePath);
        System.out.println("Image saved successfully to: " + outputImagePath);
        encoder = null;
    }


}
