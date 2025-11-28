package com.agmkhair.ecom.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_id")
    private Long brandId; // optional FK to Brand

    private String title;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(columnDefinition = "text")
    private String description;

    private String slug;

    private Integer quantity;
    private String unit;

    private BigDecimal price;
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

    private Integer priority;

    @Column(name = "offer_product")
    private String offerProduct;

    private Integer admin;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // OneToMany images
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ItemImage> images;
}
