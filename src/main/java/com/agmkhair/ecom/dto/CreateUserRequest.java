package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String phoneNo;
    private Integer status;
    private Integer type;
}
