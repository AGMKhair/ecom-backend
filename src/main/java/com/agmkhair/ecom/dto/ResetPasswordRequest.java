package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private Long userId;
    private String newPassword;
}
