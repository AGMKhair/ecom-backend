package com.agmkhair.ecom.repository;

import com.agmkhair.ecom.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    List<Cart> findByOrderId(Long orderId);
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    List<Cart> findByUserIdAndProductId(Long userId, Long productId);
    List<Cart> findByUserIdAndProductIdAndOrderIdIsNull(Long userId, Long productId);
    Optional<List<Cart>> findAllByUserIdAndOrderIdIsNotNull(Long userId);
    Optional<List<Cart>> findAllByUserIdAndOrderIdIsNull(Long userId);
    List<Cart> findByUserIdAndOrderId(Long userId, Long productId);
    Optional<List<Cart>>  findAllByUserIdAndOrderId(Long userId, Long orderId);

    @Modifying
    @Query("""
    UPDATE Cart c
    SET c.orderId = :orderId
    WHERE c.userId = :userId
      AND c.orderId IS NULL
""")
    int updateOrderForUser(@Param("userId") Long userId,
                           @Param("orderId") Long orderId);


}
