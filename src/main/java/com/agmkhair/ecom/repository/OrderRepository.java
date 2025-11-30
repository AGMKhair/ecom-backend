package com.agmkhair.ecom.repository;
import com.agmkhair.ecom.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<List<Orders>> findByUserId(Long id);
    List<Orders> findByUserIdAndIsPaid(Long userId, int isPaid);

    List<Orders> findByUserIdAndIsCompleted(Long userId, int isCompleted);
}
