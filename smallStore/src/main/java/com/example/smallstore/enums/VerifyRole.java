package com.example.smallstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VerifyRole {
    VERIFYTRUE("ROLE_TRUE"),
    VERIFYFALSE("ROLE_FALSE");

    private final String value;

}
