package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.entity.Item;
import com.agmkhair.ecom.entity.ItemImage;
import com.agmkhair.ecom.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;

    @GetMapping
    public List<Item> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> get(@PathVariable Long id) {
        Item item = service.getById(id);
        return item == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(item);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Item> create(@RequestPart("item") Item item,
                                       @RequestPart(value = "images", required = false) MultipartFile[] images) {
        Item saved = service.createItem(item, images);
        return ResponseEntity.ok(saved);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Item> update(@PathVariable Long id,
                                       @RequestPart("item") Item item,
                                       @RequestPart(value = "images", required = false) MultipartFile[] images) {
        Item saved = service.updateItem(id, item, images);
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
