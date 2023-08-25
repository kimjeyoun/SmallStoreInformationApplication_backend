package com.example.smallstore.Dto.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class UserDeleteRequest {
    @ApiModelProperty(value = "사용자 id", example="test") // DTO 변수에 대한 설명 및 다양한 설정
    private String id;

    @ApiModelProperty(value = "사용자 password", example="test1234")
    private String password;
}
