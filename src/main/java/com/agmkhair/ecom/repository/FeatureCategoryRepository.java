package com.agmkhair.ecom.repository;

import com.agmkhair.ecom.entity.FeatureCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureCategoryRepository extends JpaRepository<FeatureCategory, Integer> {
    List<FeatureCategory> findByStatus(Integer status);

}
