package com.example.demo.security;

import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static int getCurrentUserId() {
        try {
            return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (NumberFormatException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
