package com.agmkhair.ecom.dto;

public class APIResponseBuilder {

    public static <T> APIResponse<T> success(String message, T data) {
        return APIResponse.<T>builder()
                .responseCode(ResponseCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> failed(String message) {
        return APIResponse.<T>builder()
                .responseCode(ResponseCode.FAILED.getCode())
                .message(message)
                .data(null)
                .build();
    }

    public static <T> APIResponse<T> validationError(String message) {
        return APIResponse.<T>builder()
                .responseCode(ResponseCode.VALIDATION_ERROR.getCode())
                .message(message)
                .data(null)
                .build();
    }

    public static <T> APIResponse<T> notFound(String message) {
        return APIResponse.<T>builder()
                .responseCode(ResponseCode.NOT_FOUND.getCode())
                .message(message)
                .data(null)
                .build();
    }

    public static <T> APIResponse<T> internalError(String message) {
        return APIResponse.<T>builder()
                .responseCode(ResponseCode.INTERNAL_ERROR.getCode())
                .message(message)
                .data(null)
                .build();
    }
}
