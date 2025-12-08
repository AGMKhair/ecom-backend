package com.agmkhair.ecom.dto;

import com.agmkhair.ecom.entity.ItemImage;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<ItemImage> image;
}
