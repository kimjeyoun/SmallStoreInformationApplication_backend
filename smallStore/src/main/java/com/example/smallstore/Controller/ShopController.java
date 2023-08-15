package com.example.smallstore.Controller;

import com.example.smallstore.Dto.Shop.ShopNumberCheckRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import com.example.smallstore.Repository.ShopRepository;
import com.example.smallstore.Service.ShopService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/shop")
public class ShopController {
    private final ShopService shopService;
    private final ShopRepository shopRepository;

    @PostMapping("shopNumStatus")
    public ResponseEntity numberCheck(@RequestBody ShopNumberCheckRequest shopNumberCheckRequest) {
        return shopService.numberCheck(shopNumberCheckRequest);
    }

    @PostMapping("/regist")
    public ResponseEntity<String> shopSignup(@RequestBody ShopRegisterRequest shopRegisterRequest) {
        return shopService.regeist(shopRegisterRequest);
    }
}
