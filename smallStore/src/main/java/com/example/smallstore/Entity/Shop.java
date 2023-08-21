package com.example.smallstore.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

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
    @Column(nullable = false)
    private String shopOwner;

    @ApiModelProperty(value = "가게 주소", example = "경기도 군포시")
    @Column(unique = true, nullable = false)
    private String shopAddress;

    @ApiModelProperty(value = "가게 번호", required = true, example = "031-123-4567")
    @Column()
    private String shopPhoneNumber;

    @ApiModelProperty(value = "가게 로고", required = true, example = "이미지 url")
    @Column()
    private String shopLogo;

    @ApiModelProperty(value = "가게 사진", required = true, example = "이미지 url")
    @Column()
    private String shopPicture;

    @Column()
    private String categoryName;
}
