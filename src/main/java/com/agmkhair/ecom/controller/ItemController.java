package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.dto.APIResponse;
import com.agmkhair.ecom.dto.APIResponseBuilder;
import com.agmkhair.ecom.dto.ProductCreateRequest;
import com.agmkhair.ecom.dto.ProductDTO;
import com.agmkhair.ecom.entity.Products;
import com.agmkhair.ecom.entity.ItemImage;
import com.agmkhair.ecom.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;

    @GetMapping
    public List<ProductDTO> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> get(@PathVariable Long id) {
        Products products = service.getById(id);
        return products == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);
    }

 //    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
//    public APIResponse<Products> create(@RequestPart("item") Products products,
//                                        @RequestPart(value = "images", required = false) MultipartFile[] images) {
//        Products saved = service.createItem(products, images);
//        return APIResponseBuilder.success("Added Successfully",saved);
//    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public APIResponse<Products> create(
            @RequestPart("item") ProductCreateRequest req,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) {
        Products saved = service.createItem(req, images);
        return APIResponseBuilder.success("Added Successfully", saved);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Products> update(
            @PathVariable Long id,
            @RequestPart("item") ProductCreateRequest req,
            @RequestPart(value = "images", required = false) MultipartFile[] images
    ) {
        Products saved = service.updateItem(id, req, images);
        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/delete/{id}")
    public APIResponse<Boolean> delete(@PathVariable Long id) {
        return  APIResponseBuilder.success("Delete Done",service.deleteItem(id));
    }

    @GetMapping("/{id}/images")
    public List<ItemImage> images(@PathVariable Long id) {
        return service.getImages(id);
    }
}
