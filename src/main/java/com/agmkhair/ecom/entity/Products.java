package com.agmkhair.ecom.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_id")
    private Long brandId;

    private String title;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(columnDefinition = "text")
    private String description;

    private String slug;

    private Integer quantity;
    private String unit;

    private BigDecimal price;

    @Column(name = "price_sar")
    private BigDecimal priceSar;

    @Column(name = "free_shipment")
    private Boolean freeShipment;

    private Integer status;

    @Column(name = "offer_price")
    private BigDecimal offerPrice;

    @Column(name = "old_price")
    private BigDecimal oldPrice;

    @Column(name = "old_price_sar")
    private BigDecimal oldPriceSar;

    private String featured;

    @Column(name = "sizes")
    private String sizes;   // "S,M,L,XL"

    @Column(name = "colors")
    private String colors;  // "Red,Blue,Black"


    private Integer priority;

    @Column(name = "offer_product")
    private String offerProduct;

    private Integer admin;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ItemImage> images = new ArrayList<>();

}
