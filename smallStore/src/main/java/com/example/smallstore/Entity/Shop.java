package com.example.smallstore.Entity;

import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "shop")
public class Shop {
    @ApiModelProperty(value = "shopNum(auto)", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shopNum;

    @ApiModelProperty(value = "가게 이름", example = "가게 이름 test")
    @Column(nullable = false)
    private String shopName;

    @ApiModelProperty(value = "사업자 등록 번호", example = "1234567890")
    @Column(nullable = false)
    private String shopNumber;

    @ApiModelProperty(value = "가게 주인", example = "user_nickname")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ApiModelProperty(value = "가게 주소", example = "경기도 군포시")
    @Column(nullable = false, unique = true)
    private String shopAddress;

    @ApiModelProperty(value = "가게 주소 위도", example = "37", notes = "직접 작성하는 것이 아닌 백엔드에서 구현")
    @Column(nullable = false)
    private String shopLat;

    @ApiModelProperty(value = "가게 주소 경도", example = "126", notes = "직접 작성하는 것이 아닌 백엔드에서 구현")
    @Column(nullable = false)
    private String shopLng;

    @ApiModelProperty(value = "가게 번호", required = true, example = "031-123-4567")
    private String shopPhoneNumber;

    @ApiModelProperty(value = "가게 로고", required = true, example = "이미지 url")
    private String shopLogo;

    @ApiModelProperty(value = "가게 사진", required = true, example = "이미지 url")
    private String shopPicture;

    @ApiModelProperty(value = "가게 카테고리", example = "1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_num")
    @JsonIgnore
    private Category category;

    public Shop(ShopRegisterRequest shopRegisterRequest, User user, Category category, List gps) {
        this.shopName = shopRegisterRequest.getShopName();
        this.shopNumber = shopRegisterRequest.getShopNumber();
        this.user = user;
        this.shopAddress = shopRegisterRequest.getShopAddress();
        this.shopPhoneNumber = shopRegisterRequest.getShopPhoneNumber();
        this.shopLogo = shopRegisterRequest.getShopLogo();
        this.shopPicture = shopRegisterRequest.getShopPicture();
        this.category = category;
        this.shopLat = String.valueOf(gps.get(0)) ;
        this.shopLng = String.valueOf(gps.get(1));
    }
}
