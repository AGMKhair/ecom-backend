package com.agmkhair.ecom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "ip_address")
    private String ipAddress;

    private String name;

    @Column(name = "phone_no")
    private String phoneNo;

    private String email;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "shipping_address", columnDefinition = "text")
    private String shippingAddress;

    private String massage; // message?

    @Column(name = "is_paid")
    private int isPaid;

    @Column(name = "is_completed")
    private int isCompleted;

    @Column(name = "is_seen_by_admin")
    private int isSeenByAdmin;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt  = LocalDateTime.now();

    private String company;
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "division_id")
    private Long divisionId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "upzilla_id")
    private Long upzillaId;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "shipping_charge")
    private Double shippingCharge;

    @Column(name = "paid_amt")
    private Double paidAmt;

    @Column(name = "account_no")
    private String accountNo;
}
