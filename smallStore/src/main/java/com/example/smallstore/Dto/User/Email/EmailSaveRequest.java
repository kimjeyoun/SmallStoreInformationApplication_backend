package com.example.smallstore.Dto.User.Email;

import com.example.smallstore.Entity.EmailAuth;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class EmailSaveRequest {
    @ApiModelProperty(example="test@naver.com")
    private String email;
    @ApiModelProperty(example="abc123")
    private String randomCode;
    @ApiModelProperty(example="auth", value="auth/findPW 로 지정해줌(2차인증인지 비밀번호 찾기인지)")
    private String type;

    public EmailAuth toEntity() {
        return EmailAuth.builder()
                .email(email)
                .randomCode(randomCode)
                .type(type)
                .build();
    }
}
