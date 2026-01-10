package com.agmkhair.ecom.repository;
import com.agmkhair.ecom.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
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
    BigDecimal sumTotalAmountByCreatedAtBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
