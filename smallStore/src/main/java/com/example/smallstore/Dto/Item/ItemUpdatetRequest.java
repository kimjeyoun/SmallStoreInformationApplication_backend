package com.example.smallstore.Dto.Item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ItemUpdatetRequest {
    @ApiModelProperty(example = "1")
    private Long num;
    @ApiModelProperty(example = "라면")
    private String itemName;
    @ApiModelProperty(example = "1000")
    private String itemPrice;
    @ApiModelProperty(example = "맛있는 라면")
    private String itemInfo;
    @ApiModelProperty(example = "이미지 url")
    private String itemImg;
}
