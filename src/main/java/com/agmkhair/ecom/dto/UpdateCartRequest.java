package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class UpdateCartRequest {

    @NotNull
    private Long cartId;

    @NotNull
    private Integer quantity;
}
