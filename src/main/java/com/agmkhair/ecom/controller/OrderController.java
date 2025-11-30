package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.dto.OrderRequest;
import com.agmkhair.ecom.entity.Orders;
import com.agmkhair.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    public Orders create(@RequestBody OrderRequest request) {
        return service.createOrder(request);
    }

    @PutMapping("/update/{id}")
    public Orders update(@PathVariable Long id, @RequestBody OrderRequest request) {
        return service.updateOrder(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteOrder(id);
        return "Order deleted successfully";
    }

    @GetMapping
    public List<Orders> get(@RequestParam Long id) {
        return service.getOrder(id);
    }

    @GetMapping("/all")
    public List<Orders> all() {
        return service.getAllOrders();
    }

    // UNPAID
    @GetMapping("/unpaid")
    public List<Orders> getUnpaid(@RequestParam Long id) {
        return service.getUnpaidOrders(id);
    }

    // PAID
    @GetMapping("/paid")
    public List<Orders> getPaid(@RequestParam Long id) {
        return service.getPaidOrders(id);
    }

    // COMPLETED
    @GetMapping("/complete")
    public List<Orders> getCompleted(@RequestParam Long id) {
        return service.getCompletedOrders(id);
    }

}
