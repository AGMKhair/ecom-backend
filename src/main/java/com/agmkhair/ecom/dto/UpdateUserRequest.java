package com.agmkhair.ecom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private Long id;            // user id
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private Integer status;     // int
    private Integer type;       // int
}
