package com.example.smallstore.Dto.Item;

import com.example.smallstore.Entity.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ItemResponse {
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
    @ApiModelProperty(example = "1")
    private Long shopNum;

    public ItemResponse(Item item) {
        this.num = item.getNum();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.itemInfo = item.getItemInfo();
        this.itemImg = item.getItemImg();
        this.shopNum = item.getShop().getShopNum();
    }
}
