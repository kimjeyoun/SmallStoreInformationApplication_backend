package com.example.smallstore.Dto.User.SMS;

import com.example.smallstore.Entity.SMSAuth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class SMSVerifyRequest {
    @ApiModelProperty(example="test")
    private String user_id;
    @ApiModelProperty(example="01012345678")
    private String phone;
    @ApiModelProperty(example="abc123")
    private String randomCode;
    @ApiModelProperty(example="auth/findPW")
    private String type;

    public SMSAuth toEntity() {
        return SMSAuth.builder()
                .phone(phone)
                .randomCode(randomCode)
                .build();
    }
}
