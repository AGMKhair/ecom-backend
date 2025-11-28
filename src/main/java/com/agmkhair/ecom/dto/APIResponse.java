package com.agmkhair.ecom.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse<T> {

    private int responseCode;   // 00 / 99
    private String message;              // readable message
    private T data;                      // ANY response object (User, List, etc.)
}
