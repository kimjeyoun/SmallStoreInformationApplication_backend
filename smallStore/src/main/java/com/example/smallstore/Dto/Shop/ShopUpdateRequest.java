package com.example.smallstore.Dto.Shop;

import com.example.smallstore.Entity.Shop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ShopUpdateRequest {
    private String shopName, shopAddress, shopPhoneNumber, shopLogo, shopPicture, categoryName;

    public Shop toEntity() {
        return Shop.builder()
                .shopAddress(shopAddress)
                .shopPhoneNumber(shopPhoneNumber)
                .shopLogo(shopLogo)
                .shopPicture(shopPicture)
                .categoryName(categoryName)
                .build();
    }
}
