package com.agmkhair.ecom.repository;

import com.agmkhair.ecom.entity.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<List<Orders>> findByUserId(Long id);

    List<Orders> findByUserIdAndIsPaid(Long userId, int isPaid);

    List<Orders> findByUserIdAndIsCompleted(Long userId, int isCompleted);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    String countByIsCompleted(int status);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Orders o WHERE o.createdAt BETWEEN :start AND :end")
    BigDecimal sumTotalAmountByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Modifying
    @Query("UPDATE Orders o SET o.isCompleted = :status")
    int saveIsCompleted(@Param("status") int status);


    @Modifying
    @Transactional
    @Query("""
                UPDATE Orders o 
                SET o.isCompleted = :status 
                WHERE o.id = :orderId
            """)
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("status") int status);


    @Modifying
    @Transactional
    @Query("""
                UPDATE Orders o 
                SET o.isPaid = :status 
                WHERE o.id = :orderId
            """)
    int updateOrderPaidStatus(@Param("orderId") Long orderId, @Param("status") int status);


}
