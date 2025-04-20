package com.example.demo.dto.response;

import com.example.demo.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_ERROR = "error";

    String status;
    String message;
    String errorCode;
    T data;

    @Builder.Default
    Instant timestamp = Instant.now();

    String path;

    private static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        throw new IllegalStateException("No current HttpServletRequest available");
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder().status(STATUS_SUCCESS)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(null, data);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode, T data) {
        return ApiResponse.<T>builder().status(STATUS_ERROR)
                .message(message)
                .errorCode(errorCode)
                .data(data)
                .path(getCurrentHttpRequest().getRequestURI())
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, T data) {
        return error(errorCode.getMessage(), String.valueOf(errorCode.getCode()), data);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return error(message, errorCode, null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return error(errorCode, null);
    }

}
