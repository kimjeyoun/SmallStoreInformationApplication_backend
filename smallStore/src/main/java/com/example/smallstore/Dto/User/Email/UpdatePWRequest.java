package com.example.smallstore.Dto.User.Email;

import com.example.smallstore.enums.LoginType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class UpdatePWRequest {
    @ApiModelProperty(example="01012345678")
    private String phone;
    @ApiModelProperty(example="test")
    private String password;
    private LoginType loginType;
}
