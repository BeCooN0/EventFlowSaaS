package com.example.eventflowsaas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private static final String uploadDir = "uploads/posters/";

    public static String saveFile(MultipartFile file) throws IOException {
        Path copyLocation = Paths.get(uploadDir + UUID.randomUUID() + "_" + file.getOriginalFilename());
        Files.createDirectories(Paths.get(uploadDir));
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        return copyLocation.toString();
    }
}