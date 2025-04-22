package com.example.demo.exception;

import com.example.demo.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(ApiResponse.error(ErrorCode.UNCATEGORIZED));
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
                        fieldError -> {
                            String message = fieldError.getDefaultMessage();
                            ConstraintViolation<?> violation = null;

                            try {
                                violation = fieldError.unwrap(ConstraintViolation.class);
                            } catch (Exception ignored) {}

                            if (violation != null) {
                                return mapMessage(message, violation.getConstraintDescriptor().getAttributes());
                            }

                            return message != null ? message : "Invalid value";
                        },
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        ApiResponse<?> response = ApiResponse.error(errorCode.getMessage(), String.valueOf(errorCode.getCode()), errors);

        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ApiResponse<Void>> handleBadJson(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_JSON));
    }


    @ExceptionHandler({
            NoHandlerFoundException.class,
            NoResourceFoundException.class,
            HttpRequestMethodNotSupportedException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleNotFound(Exception ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ErrorCode.NOT_FOUND));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ErrorCode.INVALID_ARGUMENT_TYPE));
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
