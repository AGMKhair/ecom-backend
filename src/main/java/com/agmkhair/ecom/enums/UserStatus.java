package com.agmkhair.ecom.enums;

public enum UserStatus {
    INACTIVE(0),
    ACTIVE(1),
    BLOCKED(2);

    private final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

