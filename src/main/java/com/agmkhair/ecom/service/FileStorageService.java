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
public class FileStorageService {

    private final Path root = Paths.get("uploads/items"); // change as needed

    public FileStorageService() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize upload folder", e);
        }
    }

    public String store(MultipartFile file) {
        try {
            String ext = "";
            String original = file.getOriginalFilename();
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString() + ext;
            Path target = this.root.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    public Path load(String filename) {
        return root.resolve(filename);
    }

    public byte[] loadAsBytes(String filename) throws IOException {
        return Files.readAllBytes(load(filename));
    }

    public boolean delete(String filename) {
        try {
            return Files.deleteIfExists(load(filename));
        } catch (IOException e) {
            return false;
        }
    }
}
