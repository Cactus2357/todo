package com.example.demo.exception;

import com.example.demo.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED;
        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(ex.getBindingResult().getFieldError().getDefaultMessage());

            var constraintViolations = ex.getBindingResult().getAllErrors().getFirst()
                    .unwrap(ConstraintViolation.class);

            attributes = constraintViolations.getConstraintDescriptor().getAttributes();

        } catch (IllegalArgumentException ignored) {
        }

        String message = Objects.nonNull(attributes) ? mapMessage(errorCode.getMessage(), attributes) : errorCode.getMessage();

        return ResponseEntity.badRequest().body(ApiResponse.error(message, String.valueOf(errorCode.getCode())));

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
