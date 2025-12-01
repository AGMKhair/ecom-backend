package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureCategoryCreateRequest {
    private String name;
    private String image;
    private Integer status;
}
