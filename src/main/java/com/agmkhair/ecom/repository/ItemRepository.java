package com.agmkhair.ecom.repository;
import com.agmkhair.ecom.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Products, Long> {
    List<Products> findByCategoryIdOrderByPriorityAsc(Long categoryId);
    List<Products> findByBrandIdOrderByPriorityAsc(Long brandId);
}
