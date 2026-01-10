package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.DashboardReport;
import com.agmkhair.ecom.repository.ItemRepository;
import com.agmkhair.ecom.repository.OrderRepository;
import com.agmkhair.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository productRepository;

    public DashboardReport getDashboardReport() {

        DashboardReport report = new DashboardReport();

        // =====================
        // TOTAL COUNTS
        // =====================
        report.setTotalUser(String.valueOf(userRepository.count()));
        report.setTotalOrder(String.valueOf(orderRepository.count()));
        report.setTotalProducts(String.valueOf(productRepository.count()));

        // =====================
        // TODAY ORDERS
        // =====================
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDateTime.now();

        long todayOrders = orderRepository.countByCreatedAtBetween(start, end);
        report.setTodayOrder(String.valueOf(todayOrders));

        // =====================
        // TODAY REVENUE
        // =====================
        BigDecimal todayRevenue =
                orderRepository.sumTotalAmountByCreatedAtBetween(start, end);
        report.setTodayRevenue(String.valueOf(todayRevenue != null ? todayRevenue : BigDecimal.ZERO));



        report.setPendingOrders(
                orderRepository.countByIsCompleted(1)
        );

        report.setDeliveredOrder(orderRepository.countByIsCompleted(2));

        // =====================
        // LOW STOCK PRODUCTS
        // =====================
        report.setLowStock(
                productRepository.countByQuantityLessThan(5)
        );

        return report;
    }
}
