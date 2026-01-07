package com.agmkhair.ecom.exception;

import com.agmkhair.ecom.dto.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public APIResponse<?> handleUserExists(UserAlreadyExistsException ex) {
        return APIResponseBuilder.failed(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public APIResponse<?> handleNotFound(ResourceNotFoundException ex) {
        return APIResponseBuilder.notFound(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public APIResponse<?> handleValidation(ValidationException ex) {
        return APIResponseBuilder.validationError(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public APIResponse<?> handleGlobal(Exception ex) {
        ex.printStackTrace();
        return APIResponseBuilder.internalError("Something went wrong! " +  ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public APIResponse<?> handleMaxSize(MaxUploadSizeExceededException ex) {
        return APIResponseBuilder.failed("Image size exceeds maximum limit (10MB)");
    }
}
