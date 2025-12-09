package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class AddCardRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    private String size;

    private String color;

}
