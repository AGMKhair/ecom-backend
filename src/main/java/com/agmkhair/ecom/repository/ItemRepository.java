package com.agmkhair.ecom.repository;
import com.agmkhair.ecom.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryIdOrderByPriorityAsc(Long categoryId);
    List<Item> findByBrandIdOrderByPriorityAsc(Long brandId);
}
