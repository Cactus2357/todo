package com.example.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED("E9999", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED("E1000", "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("E1001", "Unauthorized", HttpStatus.FORBIDDEN),
    NOT_FOUND("E1002", "Not Found", HttpStatus.NOT_FOUND),

    INVALID_JSON("E1002", "Invalid or malformed JSON", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("E1003", "Validation error", HttpStatus.BAD_REQUEST),

    USER_EXISTED("E2000", "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("E2001", "User not found", HttpStatus.NOT_FOUND),
    ;

    private final String code;
    private final String message;
    private final HttpStatusCode statusCode;
}
