package com.agmkhair.ecom.dto;

import com.agmkhair.ecom.entity.ItemImage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private Long brandId;
    private Long categoryId;

    private String title;
    private String description;

    private Integer quantity;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private BigDecimal offerPrice;

    private Integer status;

    private List<ItemImage> images;

    /// API RESPONSE ONLY
    private List<String> sizes;
    private List<String> colors;

    /* ===============================
       Helper setters for conversion
       =============================== */

    public void setSizesFromString(String sizes) {
        if (sizes == null || sizes.isBlank()) {
            this.sizes = Collections.emptyList();
        } else {
            this.sizes = Arrays.stream(sizes.split(","))
                    .map(String::trim)
                    .toList();
        }
    }

    public void setColorsFromString(String colors) {
        if (colors == null || colors.isBlank()) {
            this.colors = Collections.emptyList();
        } else {
            this.colors = Arrays.stream(colors.split(","))
                    .map(String::trim)
                    .toList();
        }
    }
}
