package com.agmkhair.ecom.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.text.Normalizer;
import java.util.Locale;
import java.util.UUID;

@Service
public class StorageService {

    private final Path root = Paths.get("product_images");

    public StorageService() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize root upload folder", e);
        }
    }

    // ===============================
    // STORE IMAGE INSIDE PRODUCT FOLDER
    // ===============================
    public String store(MultipartFile file, String productTitle) {

        String safeFolder = toSafeFolderName(productTitle);
        Path productDir = root.resolve(safeFolder);

        try {
            Files.createDirectories(productDir);

            String filename =
                    UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path destination = productDir.resolve(filename);

            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );

            // 👉 DB-তে save হবে relative path
            return safeFolder + "/" + filename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store image", e);
        }
    }

    // ===============================
    // DELETE SINGLE IMAGE
    // ===============================
    public boolean delete(String relativePath) {
        try {
            Path filePath = root.resolve(relativePath);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                return true;
            }
            return false;

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file " + relativePath, e);
        }
    }

    // ===============================
    // DELETE FULL PRODUCT FOLDER (optional)
    // ===============================
    public void deleteProductFolder(String productTitle) {
        String safeFolder = toSafeFolderName(productTitle);
        Path productDir = root.resolve(safeFolder);

        if (!Files.exists(productDir)) return;

        try {
            Files.walk(productDir)
                    .sorted((a, b) -> b.compareTo(a)) // delete files first
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete product folder", e);
        }
    }

    // ===============================
    // TITLE → SAFE FOLDER NAME
    // ===============================
    private String toSafeFolderName(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\w\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-")
                .toLowerCase(Locale.ENGLISH);
    }
}
