package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductCreateRequest {

    private Long id;

    // Relations
    private Long brandId;
    private Long categoryId;

    // Basic Info
    private String title;
    private String description;
    private String unit;          // pcs, kg etc
    private Integer quantity;

    // Pricing
    private BigDecimal price;     // original price
    private BigDecimal oldPrice;  // old price (optional)
    private BigDecimal offerPrice;

    // Extra Attributes
    private List<String> sizes;   // S, M, L, XL
    private List<String> colors;  // red, blue, black

    // Flags
    private Boolean freeShipment;
    private String featured;      // yes / no
    private Integer priority;
    private Integer status;       // 1 = active, 0 = inactive
}
