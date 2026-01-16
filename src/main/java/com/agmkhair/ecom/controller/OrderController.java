package com.agmkhair.ecom.controller;

import com.agmkhair.ecom.dto.*;
import com.agmkhair.ecom.entity.Orders;
import com.agmkhair.ecom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    public APIResponse<Orders> create(@RequestBody OrderRequest request) {
        return APIResponseBuilder.success("Order Confirm", service.createOrder(request));

    }

    @PutMapping("/update/{id}")
    public APIResponse<Orders> update(@PathVariable Long id, @RequestBody OrderRequest request) {
        return APIResponseBuilder.success("Order update",  service.updateOrder(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public APIResponse<Object> delete(@PathVariable Long id) {
        service.deleteOrder(id);
        return APIResponseBuilder.success( "Order deleted successfully",null);
    }

    /// for user data
    @GetMapping
    public APIResponse<List<OrderResponse>> get(@RequestParam Long id) {
        return APIResponseBuilder.success( "Order Details",service.getOrder(id));
    }

    @GetMapping("/all")
    public APIResponse<AllOrdersResponse> all() {
        return APIResponseBuilder.success( "All Order",service.getAllOrders());
    }

    // UNPAID
    @GetMapping("/unpaid")
    public APIResponse<List<Orders>> getUnpaid(@RequestParam Long id) {
        return APIResponseBuilder.success( "Unpaid Order",service.getUnpaidOrders(id));
    }

    // PAID
    @GetMapping("/paid")
    public APIResponse<List<Orders>> getPaid(@RequestParam Long id) {

        return APIResponseBuilder.success( "Paid Order",service.getPaidOrders(id));
    }

    // COMPLETED
    @GetMapping("/complete")
    public APIResponse<List<Orders>> getCompleted(@RequestParam Long id) {
        return APIResponseBuilder.success( "Completed Order",service.getCompletedOrders(id));
    }

    ///  Update Data form Admin
    @PutMapping("/{orderId}/status")
    public ResponseEntity<APIResponse<Void>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam int status
    ) {

        service.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(APIResponseBuilder.success("Order status updated successfully", null));
    }

    @PutMapping("/{orderId}/paid/status")
    public ResponseEntity<APIResponse<Void>> updateOrderPaidStatus(
            @PathVariable Long orderId,
            @RequestParam int status
    ) {

        service.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(APIResponseBuilder.success("Order status updated successfully", null));
    }


}
