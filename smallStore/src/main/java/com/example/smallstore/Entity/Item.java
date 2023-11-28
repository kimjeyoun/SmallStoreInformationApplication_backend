package com.example.smallstore.Entity;

import com.example.smallstore.Dto.Item.ItemRegistRequest;
import com.example.smallstore.Dto.Item.ItemUpdatetRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {
    @ApiModelProperty(value = "숫자(자동)", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long num;

    @ApiModelProperty(value = "상품 이름", example = "라면")
    @Column(nullable = false)
    private String itemName;

    @ApiModelProperty(value = "상품 가격", example = "1000")
    @Column(nullable = false)
    private String itemPrice;

    @ApiModelProperty(value = "상품 정보", example = "맛있는 라면", notes = "작성되지 않아도 됨.")
    private String itemInfo;

    @ApiModelProperty(value = "상품 사진", example = "사진 url")
    private String itemImg;

    @ApiModelProperty(value = "가게", example = "어디있샵")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_num")
    @JsonIgnore
    private Shop shop;

    public Item(Shop shop, ItemRegistRequest itemRegistRequest) {
        this.itemName = itemRegistRequest.getItemName();
        this.itemPrice = itemRegistRequest.getItemPrice();
        this.itemInfo = itemRegistRequest.getItemInfo();
        this.itemImg = itemRegistRequest.getItemImg();
        this.shop = shop;
    }

    public void updateItems(ItemUpdatetRequest itemUpdatetRequest){
        this.num = itemUpdatetRequest.getNum();
        this.itemName = itemUpdatetRequest.getItemName();
        this.itemPrice = itemUpdatetRequest.getItemPrice();
        this.itemInfo = itemUpdatetRequest.getItemInfo();
        this.itemImg = itemUpdatetRequest.getItemImg();
    }
}
