package com.example.smallstore.Dto.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class UserAddWishListRequest {
    @ApiModelProperty(example = "test")
    private String id;
    @ApiModelProperty(value = "가게 숫자", example = "1")
    private Long num;

}
