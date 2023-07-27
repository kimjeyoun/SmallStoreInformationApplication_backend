package com.example.smallstore.Dto.User.RefreshToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    String id, refreshToken, ip;
}
