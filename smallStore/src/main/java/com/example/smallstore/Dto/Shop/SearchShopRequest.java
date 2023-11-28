package com.example.smallstore.Dto.Shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class SearchShopRequest {
    @ApiModelProperty(example = "어디있숍")
    private String shopName;

    @ApiModelProperty(value = "본인 위치", example = "37")
    private String userLat;

    @ApiModelProperty(value = "본인 위치", example = "126")
    private String userLng;

    @ApiModelProperty(example = "1")
    private Long categoryNum;

    @ApiModelProperty(example = "라면")
    private String keyword;
}
