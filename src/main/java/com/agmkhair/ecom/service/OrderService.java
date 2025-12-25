package com.agmkhair.ecom.service;

import com.agmkhair.ecom.dto.CartResponse;
import com.agmkhair.ecom.dto.OrderRequest;
import com.agmkhair.ecom.dto.OrderResponse;
import com.agmkhair.ecom.entity.Orders;
import com.agmkhair.ecom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    CartService cartService;


    private final OrderRepository orderRepository;  // FIXED

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

        Orders result =  orderRepository.save(order);
        if (result != null){
            cartService.updateOrder(request.getUserId(),result.getId());
        }

         return result;
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

    public List<OrderResponse> getOrder(Long userId) {

        List<Orders> orders = orderRepository
                .findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Orders not found"));

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
            response.setItems(items);

            responseList.add(response);
        }

        return responseList;
    }


    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
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


    private  String getPaymentMethod(String name){

        switch(name.toLowerCase())
        {
            case "cash":
                return  "1";
            case "bkash":
                return  "2";
            case "nagad":
                return  "3";
            default:
                return "0";
        }
    }
}
