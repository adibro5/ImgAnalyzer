package com.example.ImgAnalyzer.Base64;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Bean
public final class Base64Encoder extends Base64Converter{

    @Override
    public void encode(String ...fileString) throws Exception {
        byte[] imageBytes = Base64.getDecoder().decode(fileString[0]);

        // 1. Get the path object for the file
        Path path = Paths.get(fileString[1]);

        // 2. Extract the parent directory path
        Path parentDirectory = path.getParent();

        // 3. Create the missing folders if they don't exist
        if (parentDirectory != null && !Files.exists(parentDirectory)) {
            Files.createDirectories(parentDirectory);
        }

        // 4. Write bytes to the target file destination
        try (OutputStream outputStream = new FileOutputStream(fileString[1])) {
            outputStream.write(imageBytes);
        }
    }

    @Override
    public String decode(File ...file) throws Exception {
        throw new RuntimeException("You cannot decode using this context.");
    }

    public static String cleanBase64(String input) {
        if (input == null) {
            return "";
        }

        // 1. Remove all spaces, line breaks, or tabs that might cause issues
        String cleaned = input.replaceAll("\\s+", "");

        // 2. Safely strip the "data:image/...;base64," prefix if present
        if (cleaned.contains(",")) {
            cleaned = cleaned.substring(cleaned.indexOf(",") + 1);
        }

        return cleaned;
    }

}
