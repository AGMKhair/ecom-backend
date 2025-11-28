package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.entity.Brand;
import com.agmkhair.ecom.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService service;

    @GetMapping
    public List<Brand> all() { return service.all(); }

    @PostMapping
    public Brand create(@RequestBody Brand b) { return service.create(b); }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(@PathVariable Long id, @RequestBody Brand b) {
        Brand updated = service.update(id, b);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
