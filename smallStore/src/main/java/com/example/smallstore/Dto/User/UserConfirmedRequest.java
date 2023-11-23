package com.example.smallstore.Dto.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class UserConfirmedRequest {
    @ApiModelProperty(example = "test")
    private String id;
    @ApiModelProperty(example = "01012345678")
    private String phone;
}
