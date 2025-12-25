package com.agmkhair.ecom.dto;

import lombok.Data;

@Data
public class CartResponse {
    private Long id;
    private Integer quantity;
    private String size;
    private String color;

    private ProductDTO product;
}
