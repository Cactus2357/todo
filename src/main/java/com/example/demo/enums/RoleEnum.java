package com.example.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    ADMIN(1, "admin"),
    USER(2, "user"),
    MODERATOR(3, "moderator");

    private final int roleId;
    private final String roleName;
}
