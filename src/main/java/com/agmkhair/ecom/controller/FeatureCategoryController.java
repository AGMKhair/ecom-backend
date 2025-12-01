package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.dto.APIResponse;
import com.agmkhair.ecom.dto.APIResponseBuilder;
import com.agmkhair.ecom.dto.FeatureCategoryCreateRequest;
import com.agmkhair.ecom.dto.FeatureCategoryUpdateRequest;
import com.agmkhair.ecom.entity.FeatureCategory;
import com.agmkhair.ecom.service.FeatureCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feature-category")
@RequiredArgsConstructor
public class FeatureCategoryController {

    private final FeatureCategoryService service;

    // CREATE
    @PostMapping("/create")
    public APIResponse<FeatureCategory> create(@RequestBody FeatureCategoryCreateRequest req) {
        return APIResponseBuilder.success("Created", service.create(req));
    }

    // UPDATE
    @PutMapping("/update")
    public APIResponse<FeatureCategory> update(@RequestBody FeatureCategoryUpdateRequest req) {
        return APIResponseBuilder.success("Updated", service.update(req));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public APIResponse<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return APIResponseBuilder.success("Deleted", null);
    }

    // GET ONE
    @GetMapping("/get/{id}")
    public APIResponse<FeatureCategory> get(@PathVariable Integer id) {
        return APIResponseBuilder.success("Success", service.get(id));
    }

    // GET ALL
    @GetMapping("/all")
    public APIResponse<List<FeatureCategory>> getAll(@RequestParam(required = false) Integer status) {
        return APIResponseBuilder.success(
                "Success",
                service.getAll(status)
        );
    }

}
