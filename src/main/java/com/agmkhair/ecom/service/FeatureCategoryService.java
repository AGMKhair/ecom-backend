package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.FeatureCategoryCreateRequest;
import com.agmkhair.ecom.dto.FeatureCategoryUpdateRequest;
import com.agmkhair.ecom.entity.FeatureCategory;
import com.agmkhair.ecom.repository.FeatureCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureCategoryService {

    private final FeatureCategoryRepository repo;

    // CREATE
    public FeatureCategory create(FeatureCategoryCreateRequest req) {
        FeatureCategory fc = new FeatureCategory();
        fc.setName(req.getName());
        fc.setImage(req.getImage());
        fc.setStatus(req.getStatus());
        return repo.save(fc);
    }

    // UPDATE
    public FeatureCategory update(FeatureCategoryUpdateRequest req) {
        FeatureCategory fc = repo.findById(req.getId())
                .orElseThrow(() -> new RuntimeException("Feature category not found"));

        if (req.getName() != null) fc.setName(req.getName());
        if (req.getImage() != null) fc.setImage(req.getImage());
        if (req.getStatus() != null) fc.setStatus(req.getStatus());

        return repo.save(fc);
    }

    // DELETE
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    // GET ONE
    public FeatureCategory get(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Feature category not found"));
    }

    // GET ALL
    public List<FeatureCategory> getAll(Integer status) {
        if (status != null) {
            return repo.findByStatus(status);
        }
        return repo.findAll();
    }

}
