package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private Long userId;
    private String shippingAddress;
    private String phoneNumber;
    private String name;
    private String paymentMethod;
    private Double totalAmount;
    private List<CartResponse> items;
}

