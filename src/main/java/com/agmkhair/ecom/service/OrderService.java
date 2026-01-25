package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.AllOrdersResponse;
import com.agmkhair.ecom.dto.CartResponse;
import com.agmkhair.ecom.dto.OrderRequest;
import com.agmkhair.ecom.dto.OrderResponse;
import com.agmkhair.ecom.entity.Orders;
import com.agmkhair.ecom.exception.ResourceNotFoundException;
import com.agmkhair.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;  // FIXED
    @Autowired
    CartService cartService;

    public Orders createOrder(OrderRequest request) {
        Orders order = new Orders();

        order.setUserId(request.getUserId());
        order.setPaymentId(getPaymentMethod(request.getPaymentId()));
        order.setName(request.getName());
        order.setPhoneNo(request.getPhoneNo());
        order.setEmail(request.getEmail());
        order.setShippingAddress(request.getShippingAddress());
        order.setMassage(request.getMassage());
        order.setTotalAmount(request.getTotalAmount());
        order.setShippingCharge(request.getShippingCharge());
        order.setPaidAmt(request.getPaidAmt());
        order.setAccountNo(request.getAccountNo());
        order.setTransactionId(request.getTransactionId());

        // default values
        order.setIsPaid(0);
        order.setIsCompleted(0);
        order.setIsSeenByAdmin(0);

        Orders result = orderRepository.save(order);
        if (result != null) {
            cartService.updateOrder(request.getUserId(), result.getId());
        }

        return result;
    }

    public Orders updateOrder(Long id, OrderRequest request) {
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setName(request.getName());
        order.setPhoneNo(request.getPhoneNo());
        order.setEmail(request.getEmail());
        order.setShippingAddress(request.getShippingAddress());
        order.setMassage(request.getMassage());
        order.setTotalAmount(request.getTotalAmount());
        order.setPaidAmt(request.getPaidAmt());
        order.setUpdatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderResponse> getOrder(Long userId) {

        List<Orders> orders = orderRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Orders not found"));

        List<OrderResponse> responseList = new ArrayList<>();


        for (Orders order : orders) {

            // 🔹 get cart items by orderId
            List<CartResponse> items = cartService.getDetailsByOrderID(userId, order.getId());

            // 🔹 map order → response
            OrderResponse response = new OrderResponse();
            response.setId(order.getId());
            response.setUserId(order.getUserId());
            response.setShippingAddress(order.getShippingAddress());
            response.setTotalAmount(order.getTotalAmount());
            response.setStatus(order.getIsCompleted());
            response.setItems(items);

            responseList.add(response);
        }

        return responseList;
    }

    public AllOrdersResponse getAllOrders() {

        List<Orders> orders = orderRepository.findAll();

        // ✅ Initialize response
        AllOrdersResponse response = new AllOrdersResponse();

        // ✅ Initialize lists
        List<Orders> pendingOrders = new ArrayList<>();
        List<Orders> completeOrders = new ArrayList<>();
        List<Orders> cancelOrders = new ArrayList<>();
        List<Orders> deliveredOrder = new ArrayList<>();
        List<Orders> returnOrders = new ArrayList<>();
        List<Orders> paidOrders = new ArrayList<>();

        // ✅ Categorize orders
        for (Orders o : orders) {
            switch (o.getIsCompleted()) {
                case 0 -> pendingOrders.add(o);     // Pending
                case 1 -> deliveredOrder.add(o);    // Completed
                case 2 -> returnOrders.add(o);        // Paid (example)
                case 3 -> completeOrders.add(o);        // Paid (example)
                case 99 -> cancelOrders.add(o);     // Cancelled
            }
        }

        // ✅ Set lists
        response.setPendingOrder(pendingOrders);
        response.setDeliveredOrder(deliveredOrder);
        response.setReturnOrder(returnOrders);
        response.setCompleteOrder(completeOrders);
        response.setCancelOrder(cancelOrders);
        response.setPaidOrder(completeOrders);

        // ✅ Set totals
        response.setTotalOrder(orders.size());
        response.setTotalPendingOrder(pendingOrders.size());
        response.setTotalDeliveredOrder(deliveredOrder.size());
        response.setTotalReturnOrder(returnOrders.size());
        response.setTotalCompleteOrder(completeOrders.size());
        response.setTotalPaidOrder(completeOrders.size());
        response.setTotalCancelOrder(cancelOrders.size());

        return response;
    }

    public List<Orders> getUnpaidOrders(Long userId) {
        return orderRepository.findByUserIdAndIsPaid(userId, 0);
    }

    public List<Orders> getPaidOrders(Long userId) {
        return orderRepository.findByUserIdAndIsPaid(userId, 1);
    }

    public List<Orders> getCompletedOrders(Long userId) {
        return orderRepository.findByUserIdAndIsCompleted(userId, 1);
    }

    ///  Admin Section

    public List<CartResponse> getOrderDetails(Long orderId) {
        return cartService.getDetailsByOrderID(orderId);
    }

    public String orderStatusUpdate(int status) {
        orderRepository.saveIsCompleted(status);
        return "Order status updated successfully";
    }

    public void updateOrderStatus(Long orderId, int status) {
        int updated = orderRepository.updateOrderStatus(orderId, status);

        if (updated == 0) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }
    }

    private String getPaymentMethod(String name) {

        switch (name.toLowerCase()) {
            case "cash":
                return "1";
            case "bkash":
                return "2";
            case "nagad":
                return "3";
            default:
                return "0";
        }
    }
}
