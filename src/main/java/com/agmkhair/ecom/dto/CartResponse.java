package com.agmkhair.ecom.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartResponse {
    private Long id;
    private Integer quantity;
    private BigDecimal rate;
    private BigDecimal totalAmt;
    private String size;

    private ProductDTO product;
}
