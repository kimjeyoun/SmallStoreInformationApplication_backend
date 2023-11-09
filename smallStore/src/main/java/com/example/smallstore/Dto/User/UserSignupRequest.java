package com.example.smallstore.Dto.User;

import com.example.smallstore.Entity.User;
import com.example.smallstore.enums.LoginType;
import com.example.smallstore.enums.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class UserSignupRequest {
    @ApiModelProperty(example = "test")
    private String id;
    @ApiModelProperty(example = "01012345678")
    private String phone;
    @ApiModelProperty(example = "test")
    private String password;
    @ApiModelProperty(example = "경기도 군포시")
    private String address;
    @ApiModelProperty(example = "test")
    private String nickname;
    @ApiModelProperty(value = "kakaoLogin/localLogin 로그인인지 구분해줌, kakao 로그인 시 2차 인증 이메일은 안함.")
    private LoginType loginType;
    private UserRole userRole;
    @ApiModelProperty(example = "T/F")
    private boolean emailConfirmed;

    public User toEntity() {
        return User.builder()
                .id(id)
                .phone(phone)
                .password(password)
                .address(address)
                .userRole(userRole)
                .nickname(nickname)
                .emailConfirmed(emailConfirmed)
                .loginType(loginType)
                .build();
    }
}
