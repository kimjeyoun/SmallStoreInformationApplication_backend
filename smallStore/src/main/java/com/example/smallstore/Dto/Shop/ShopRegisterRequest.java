package com.example.smallstore.Dto.Shop;

import com.example.smallstore.Entity.Category;
import com.example.smallstore.Entity.Shop;
import com.example.smallstore.Entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ShopRegisterRequest {
    @ApiModelProperty(example = "어디있숍")
    private String shopName;
    @ApiModelProperty(example = "0000000001")
    private String shopNumber;
    @ApiModelProperty(example = "test")
    private String id;
    @ApiModelProperty(example = "경기도 군포시")
    private String shopAddress;
    @ApiModelProperty(example = "010-1234-5678")
    private String shopPhoneNumber;
    @ApiModelProperty(example = "img-url")
    private String shopLogo;
    @ApiModelProperty(example = "img-url")
    private String shopPicture;
    @ApiModelProperty(example = "1")
    private Long categoryNum;
}

