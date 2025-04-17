package com.example.demo.exception;

import com.example.demo.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MSG_VALIDATION_ERROR = "Validation error";

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED;

        return ResponseEntity.internalServerError().body(ApiResponse.error(errorCode));
    }

    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.error(errorCode));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingException(MethodArgumentNotValidException ex) {
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("Invalid value"),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        ApiResponse<?> response = ApiResponse.error(errorCode.getMessage(), String.valueOf(errorCode.getCode()), errors);

        return ResponseEntity.badRequest().body(response);
    }

    private String mapMessage(String message, Map<String, Object> attributes) {
        if (message == null || attributes == null)
            return message;

        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key == null || value == null) continue;

            String placeholder = "{" + key + "}";
            message = message.replace(placeholder, String.valueOf(value));
        }

        return message;
    }

}
