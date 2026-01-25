package com.agmkhair.ecom.entity;

import com.agmkhair.ecom.enums.UserStatus;
import com.agmkhair.ecom.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;      // encrypted with BCrypt

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(nullable = false)
    private Integer status = UserStatus.ACTIVE.getCode();

    @Column(nullable = false)
    private Integer type = UserType.CUSTOMER.ordinal();          // ADMIN / USER


    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "donor")
    private String donor;     // enum('Yes','No') stored as string

    @Column(name = "last_donate")
    private LocalDate lastDonate;

    @Column(name = "next_donate")
    private LocalDate nextDonate;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;


    @Column(name = "street_address", length = 191)
    private String streetAddress;

    @Column(name = "ip_address", length = 191)
    private String ipAddress;

    @Column(length = 191)
    private String avatar;

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "division_id")
    private Integer divisionId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "remember_token", length = 100)
    private String rememberToken;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt  = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt  = LocalDateTime.now();
}
