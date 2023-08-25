package com.example.smallstore.Dto.User.Email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class EmailResponse {
    @ApiModelProperty(example="test@naver.com")
    private String email;
    @ApiModelProperty(example="abc123")
    private String randomCode;
    @ApiModelProperty(example="2023-01-01/01:00")
    private String createdDate;
}
