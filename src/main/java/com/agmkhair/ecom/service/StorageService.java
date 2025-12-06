package com.agmkhair.ecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    private final Path root = Paths.get("/Applications/XAMPP/xamppfiles/htdocs/product_images");

    public StorageService() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public String store(MultipartFile file) {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            Files.copy(file.getInputStream(), this.root.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }

        return filename;
    }

    public boolean delete(String filename) {
        try {
            Path filePath = root.resolve(filename);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            } else {
                return false; // file not found
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file " + filename, e);
        }
    }
}

