package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.dto.APIResponse;
import com.agmkhair.ecom.dto.APIResponseBuilder;
import com.agmkhair.ecom.utils.CommonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ImageController {

    @PostMapping("/upload-image")
public APIResponse<String> uploadImage(
        @RequestParam("file") MultipartFile file
) throws IOException {

    String uploadDir = "product_images/";
    File dir = new File(uploadDir);
    if (!dir.exists()) dir.mkdirs();

    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    Path path = Paths.get(uploadDir + fileName);

    Files.copy(file.getInputStream(), path);

    return APIResponseBuilder.success(
            "Image uploaded",
            CommonUtils.IMAGE_URL + fileName
    );
}

}
