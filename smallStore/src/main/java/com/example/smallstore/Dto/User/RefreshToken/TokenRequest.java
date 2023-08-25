package com.example.smallstore.Dto.User.RefreshToken;

import com.example.smallstore.Entity.Token;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    @ApiModelProperty(value = "사용자 id", example="test") // DTO 변수에 대한 설명 및 다양한 설정
    private String id;
    @ApiModelProperty(example="~")
    private String refreshToken;
    @ApiModelProperty(example="127.0.0.1")
    private String ip;

    public Token toEntity() {
        return Token.builder()
                .refreshToken(refreshToken)
                .ip(ip)
                .build();
    }
}
