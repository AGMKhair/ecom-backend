package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.entity.Products;
import com.agmkhair.ecom.entity.ItemImage;
import com.agmkhair.ecom.service.ItemService;
import lombok.RequiredArgsConstructor;
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
    public List<Products> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> get(@PathVariable Long id) {
        Products products = service.getById(id);
        return products == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);
    }

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<Products> create(@RequestPart("item") Products products,
                                           @RequestPart(value = "images", required = false) MultipartFile[] images) {
        Products saved = service.createItem(products, images);
        return ResponseEntity.ok(saved);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Products> update(@PathVariable Long id,
                                           @RequestPart("item") Products products,
                                           @RequestPart(value = "images", required = false) MultipartFile[] images) {
        Products saved = service.updateItem(id, products, images);
        return saved == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.deleteItem(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/images")
    public List<ItemImage> images(@PathVariable Long id) {
        return service.getImages(id);
    }
}
