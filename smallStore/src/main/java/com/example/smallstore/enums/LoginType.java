package com.example.smallstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginType {
    localLogin("ROLE_LOCAL"),
    kakaoLogin("ROLE_KAKAO");

    private final String value;
}
