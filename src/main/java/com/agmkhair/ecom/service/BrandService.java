package com.agmkhair.ecom.service;

import com.agmkhair.ecom.entity.Brand;
import com.agmkhair.ecom.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepo;

    public Brand create(Brand b) {
        b.setCreatedAt(LocalDateTime.now());
        return brandRepo.save(b);
    }

    public Brand update(Long id, Brand b) {
        return brandRepo.findById(id).map(ex -> {
            ex.setName(b.getName());
            ex.setDescription(b.getDescription());
            ex.setImage(b.getImage());
            ex.setCategoryId(b.getCategoryId());
            ex.setUpdatedAt(LocalDateTime.now());
            return brandRepo.save(ex);
        }).orElse(null);
    }

    public List<Brand> all() { return brandRepo.findAll(); }

    public boolean delete(Long id) {
        return brandRepo.findById(id).map(b -> { brandRepo.delete(b); return true; }).orElse(false);
    }
}
