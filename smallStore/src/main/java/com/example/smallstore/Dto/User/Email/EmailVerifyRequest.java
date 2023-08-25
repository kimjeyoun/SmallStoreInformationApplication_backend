package com.example.smallstore.Dto.User.Email;

import com.example.smallstore.enums.VerifyRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class EmailVerifyRequest {
    @ApiModelProperty(example="test@naver.com")
    private String email;
    @ApiModelProperty(example="abc123")
    private String randomCode;
    private VerifyRole verifyRole;
}
