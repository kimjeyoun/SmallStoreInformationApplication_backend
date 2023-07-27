package com.example.smallstore.Dto.User.RefreshToken;

import com.example.smallstore.Entity.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    String id, refreshToken, ip;

    public Token toEntity() {
        return Token.builder()
                .refreshToken(refreshToken)
                .ip(ip)
                .build();
    }
}
