package com.agmkhair.ecom.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private String paymentId;

    private String ipAddress;
    private String name;
    private String phoneNo;
    private String email;
    private String transactionId;
    private String shippingAddress;
    private String massage;

    private Integer isPaid;
    private Integer isCompleted;
    private Integer isSeenByAdmin;

    private String company;
    private String city;
    private String zipCode;

    private Long divisionId;
    private Long districtId;
    private Long upzillaId;

    private Double totalAmount;
    private Double shippingCharge;
    private Double paidAmt;
    private String accountNo;
}
