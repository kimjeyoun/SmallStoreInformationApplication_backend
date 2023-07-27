package com.example.smallstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER("ROLE_USER"),
    SHOPOWNE("ROLE_SHOPOWNER"),
    ADMIN("ROLE_ADMIN");

    private final String value;
}
