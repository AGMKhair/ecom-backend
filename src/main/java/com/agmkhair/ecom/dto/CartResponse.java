package com.agmkhair.ecom.dto;

import lombok.Data;

@Data
public class CartResponse {
    private Long id;
    private Integer quantity;
//    private BigDecimal rate;
//    private BigDecimal totalAmt;
    private String size;
    private String color;

    private ProductDTO product;
}
