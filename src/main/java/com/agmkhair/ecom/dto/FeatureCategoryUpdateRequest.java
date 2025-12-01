package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureCategoryUpdateRequest {
    private Integer id;
    private String name;
    private String image;
    private Integer status;
}
