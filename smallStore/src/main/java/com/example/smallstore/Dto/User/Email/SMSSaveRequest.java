package com.example.smallstore.Dto.User.Email;

import com.example.smallstore.Entity.SMSAuth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class SMSSaveRequest {
    @ApiModelProperty(example="01012345678")
    private String phone;
    @ApiModelProperty(example="abc123")
    private String randomCode;
    @ApiModelProperty(example="auth", value="auth/findPW 로 지정해줌(2차인증인지 비밀번호 찾기인지)")
    private String type;

    public SMSAuth toEntity() {
        return SMSAuth.builder()
                .phone(phone)
                .randomCode(randomCode)
                .type(type)
                .build();
    }
}
