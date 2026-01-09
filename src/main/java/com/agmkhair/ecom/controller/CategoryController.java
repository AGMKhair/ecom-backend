package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.dto.APIResponse;
import com.agmkhair.ecom.dto.APIResponseBuilder;
import com.agmkhair.ecom.entity.Category;
import com.agmkhair.ecom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/main")
    public List<Category> getMainMenus() {
        return service.getMainMenus();
    }

    @GetMapping("/sub/{parentId}")
    public List<Category> getSubMenus(@PathVariable Long parentId) {
        return service.getSubMenus(parentId);
    }

    @GetMapping("/sub-sub/{subParentId}")
    public List<Category> getSubSubMenus(@PathVariable Long subParentId) {
        return service.getSubSubMenus(subParentId);
    }

    @GetMapping("/all")
    public List<Map<String, Object>> getFullMenuTree() {
        return service.getFullMenuTree();
    }

    @PostMapping("/add")
    public APIResponse<Category> addCategory(@RequestBody Category req) {

        return APIResponseBuilder.success("Added Successfully",service.addMenu(req));
    }

}

