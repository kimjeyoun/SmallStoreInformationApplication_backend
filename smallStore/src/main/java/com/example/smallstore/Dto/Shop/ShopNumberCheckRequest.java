package com.example.smallstore.Dto.Shop;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class ShopNumberCheckRequest {
    private List<String> b_no ;
}
