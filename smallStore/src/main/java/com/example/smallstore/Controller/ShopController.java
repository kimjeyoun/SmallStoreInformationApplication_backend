package com.example.smallstore.Controller;

import com.example.smallstore.Dto.Shop.ShopNumberCheckRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import com.example.smallstore.Service.ShopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/shop")
public class ShopController {
    private final ShopService shopService;

    // 사업자 등록 번호 상태 확인(운영하고 있는지 확인)
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "사업자 번호 인증 성공")
            , @ApiResponse(code = 400, message = "사업자 번호 인증 실패")
    })
    @ApiOperation(value = "사업자등록번호 상태 확인")
    @PostMapping("shopNumStatus")
    public ResponseEntity numberCheck(@RequestBody ShopNumberCheckRequest shopNumberCheckRequest) {
        return shopService.numberCheck(shopNumberCheckRequest);
    }

    // 가게 등록
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "가게 등록 성공")
    })
    @ApiOperation(value = "가게 등록")
    @PostMapping("/regist")
    public ResponseEntity<String> shopSignup(@RequestBody ShopRegisterRequest shopRegisterRequest) {
        return shopService.regeist(shopRegisterRequest);
    }
}
