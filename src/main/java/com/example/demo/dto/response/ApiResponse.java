package com.example.demo.dto.response;

import com.example.demo.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    String status;
    String message;
    String errorCode;
    T data;

    @Builder.Default
    Instant timestamp = Instant.now();

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder().status("success").message(message).data(data).build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(null, data);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode) {
        return ApiResponse.<T>builder().status("error").message(message).errorCode(errorCode).build();
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return error(errorCode.getMessage(), String.valueOf(errorCode.getCode()));
    }
}
