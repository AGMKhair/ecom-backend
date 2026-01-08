package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

// ProductCreateRequest.java
@Getter
@Setter
public class ProductCreateRequest {

    private Long id;
    private Long brandId;
    private Long categoryId;

    private String title;
    private String description;
    private Integer quantity;
    private String unit;

    private BigDecimal price;
    private BigDecimal offerPrice;

    private Integer status;
}
