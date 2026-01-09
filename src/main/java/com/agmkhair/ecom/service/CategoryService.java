package com.agmkhair.ecom.service;

import com.agmkhair.ecom.entity.Category;
import com.agmkhair.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getMainMenus() {
        return categoryRepository.findMainMenus();
    }

    public List<Category> getSubMenus(Long parentId) {
        return categoryRepository.findSubMenus(parentId);
    }

    public List<Category> getSubSubMenus(Long subParentId) {
        return categoryRepository.findSubSubMenus(subParentId);
    }

    public List<Map<String, Object>> getFullMenuTree() {

        List<Category> mainMenus = getMainMenus();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Category main : mainMenus) {

            Map<String, Object> menuObj = new HashMap<>();
            menuObj.put("id", main.getId());
            menuObj.put("title", main.getTitle());

            List<Category> subMenus = getSubMenus(main.getId());
            List<Map<String, Object>> subList = new ArrayList<>();

            for (Category sub : subMenus) {

                Map<String, Object> subObj = new HashMap<>();
                subObj.put("id", sub.getId());
                subObj.put("title", sub.getTitle());

                List<Category> subSubMenus = getSubSubMenus(sub.getId());

                // ↓ Here we map sub-sub menus also
                List<Map<String, Object>> subSubList = new ArrayList<>();
                for (Category subSub : subSubMenus) {
                    Map<String, Object> subSubObj = new HashMap<>();
                    subSubObj.put("id", subSub.getId());
                    subSubObj.put("title", subSub.getTitle());
                    subSubList.add(subSubObj);
                }

                subObj.put("children", subSubList);
                subList.add(subObj);
            }

            menuObj.put("children", subList);

            result.add(menuObj);
        }

        return result;
    }


    public Category addMenu(Category value) {
        return categoryRepository.save(value);
    }

}
