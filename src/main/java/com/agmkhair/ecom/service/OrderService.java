package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.OrderRequest;
import com.agmkhair.ecom.entity.Orders;
import com.agmkhair.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;


    public Orders createOrder(OrderRequest request) {
        Orders order = new Orders();
        order.setUserId(request.getUserId());
        order.setName(request.getName());
        order.setPhoneNo(request.getPhoneNo());
        order.setEmail(request.getEmail());
        order.setShippingAddress(request.getShippingAddress());
        order.setMassage(request.getMassage());
        order.setTotalAmount(request.getTotalAmount());
        order.setShippingCharge(request.getShippingCharge());
        order.setPaidAmt(request.getPaidAmt());
        order.setAccountNo(request.getAccountNo());
        order.setIsPaid(0);
        order.setIsCompleted(0);
        order.setIsSeenByAdmin(0);

        return orderRepository.save(order);
    }

    public Orders updateOrder(Long id, OrderRequest request) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setName(request.getName());
        order.setPhoneNo(request.getPhoneNo());
        order.setEmail(request.getEmail());
        order.setShippingAddress(request.getShippingAddress());
        order.setMassage(request.getMassage());
        order.setTotalAmount(request.getTotalAmount());
        order.setPaidAmt(request.getPaidAmt());

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    public Orders getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }


    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
