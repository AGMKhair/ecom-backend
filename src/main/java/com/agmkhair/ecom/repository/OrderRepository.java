package com.agmkhair.ecom.repository;
import com.agmkhair.ecom.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> { }
