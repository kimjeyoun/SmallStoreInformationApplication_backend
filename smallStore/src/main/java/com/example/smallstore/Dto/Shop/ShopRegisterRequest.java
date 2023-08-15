package com.example.smallstore.Dto.Shop;

import com.example.smallstore.Entity.Shop;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ShopRegisterRequest {
    private String shopName, shopNumber, shopOwner, shopAddress, shopPhoneNumber, shopLogo, shopPicture, categoryName;

    public Shop toEntity() {
        return Shop.builder()
                .shopName(shopName)
                .shopNumber(shopNumber)
                .shopOwner(shopOwner)
                .shopAddress(shopAddress)
                .shopPhoneNumber(shopPhoneNumber)
                .shopLogo(shopLogo)
                .shopPicture(shopPicture)
                .categoryName(categoryName)
                .build();
    }
}
