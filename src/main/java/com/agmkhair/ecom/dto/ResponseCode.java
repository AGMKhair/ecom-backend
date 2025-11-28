package com.agmkhair.ecom.dto;

public enum ResponseCode {
    SUCCESS(200),
    FAILED(99),
    NOT_FOUND(400),
    INTERNAL_ERROR(500),
    VALIDATION_ERROR(100);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
