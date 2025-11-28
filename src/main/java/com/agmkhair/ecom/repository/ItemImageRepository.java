package com.agmkhair.ecom.repository;
import com.agmkhair.ecom.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findByItemId(Long itemId);
}
