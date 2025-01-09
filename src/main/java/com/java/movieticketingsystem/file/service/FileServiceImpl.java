package com.java.movieticketingsystem.file.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${app.file.upload-dir}")
    private String uploadDir;
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Path.of(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }
    @Override
    public String store(MultipartFile file, long userId) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Failed to store empty file.");
            }
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = userId + "_" + System.currentTimeMillis() + fileExtension;
            Path targetLocation = Path.of(uploadDir + newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file.", ex);
        }
    }
    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Path.of(uploadDir + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found: " + fileName, ex);
        }
    }
    @Override
    public String deleteFile(String fileName) {
        try {
            Path filePath = Path.of(uploadDir + fileName);
            boolean deleted = Files.deleteIfExists(filePath);
            if (deleted) {
                return "File deleted successfully";
            } else {
                return "File not found";
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to delete file: " + fileName, ex);
        }
    }

}
