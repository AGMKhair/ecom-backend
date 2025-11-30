package com.agmkhair.ecom.repository;

import com.agmkhair.ecom.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    List<Cart> findByOrderId(Long orderId);
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    List<Cart> findByUserIdAndProductId(Long userId, Long productId);

    }
