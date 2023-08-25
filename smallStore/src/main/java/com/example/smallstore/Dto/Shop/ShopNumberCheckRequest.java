package com.example.smallstore.Dto.Shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ShopNumberCheckRequest {
    @ApiModelProperty(value = "사업자 상태 코드 출력(01 : 계속사업자, 02: 휴업자, 03 : 폐업자)", example="01")
    private List<String> b_no ;
}
