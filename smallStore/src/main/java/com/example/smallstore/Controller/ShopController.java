package com.example.smallstore.Controller;

import com.example.smallstore.Dto.Shop.ShopNumberCheckRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import com.example.smallstore.Repository.ShopRepository;
import com.example.smallstore.Service.S3Serivce;
import com.example.smallstore.Service.ShopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/shop")
public class ShopController {
    private final ShopService shopService;
    private final ShopRepository shopRepository;
    private final S3Serivce s3Serivce;

    // 사업자 등록 번호 상태 확인(운영하고 있는지 확인)
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "사용 가능한 사업자 번호입니다.")
            , @ApiResponse(code = 400, message = "사용 불가능한 사업자 번호입니다.")
    })
    @ApiOperation(value = "사업자등록번호 상태 확인")
    @PostMapping("shopNumStatus")
    public ResponseEntity numberCheck(@RequestBody ShopNumberCheckRequest shopNumberCheckRequest) {
        return shopService.numberCheck(shopNumberCheckRequest);
    }

    // 가게 등록
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "가게 등록이 완료되었습니다.")
    })
    @ApiOperation(value = "가게 등록")
    @PostMapping("/regist")
    public ResponseEntity<String> shopSignup(@RequestBody ShopRegisterRequest shopRegisterRequest) {
        return shopService.regeist(shopRegisterRequest);
    }

    // 검색

    // 조건별로 가게 보여주기

    // 로고 이미지 저장
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "로고 이미지 저장했습니다."),
            @ApiResponse(code = 400, message = "로고 이미지 저장하지 못했습니다.")
    })
    @ApiOperation(value = "로고 이미지 저장")
    @GetMapping("/saveLogoImg")
    public ResponseEntity<String> saveLogoImg(MultipartFile fileName, HttpServletRequest request) {
        return shopService.saveLogoImg(fileName, request);
    }

    // 가게 이미지 저장
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "가게 이미지 저장했습니다."),
            @ApiResponse(code = 400, message = "가게 이미지 저장하지 못했습니다.")
    })
    @ApiOperation(value = "가게 이미지 저장")
    @GetMapping("/saveShopImg")
    public ResponseEntity<String> saveShopImg(MultipartFile fileName, HttpServletRequest request) {
        return shopService.saveShopImg(fileName, request);
    }

    // 이미지 가져오기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "이미지 가져오기 성공했습니다.")
    })
    @ApiOperation(value = "이미지 가져오기")
    @GetMapping("/downloadLogoImg")
    public ResponseEntity<String> downloadLogoImg(String originalFilename) {
        return shopService.downloadLogoImg(originalFilename);
    }

    // 로고 이미지 삭제하기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "로고 이미지 삭제했습니다.")
    })
    @ApiOperation(value = "로고 이미지 삭제하기")
    @DeleteMapping("/deleteLogoImage")
    public ResponseEntity<String> deleteLogoImage(String originalFilename, HttpServletRequest request) {
        return shopService.deleteLogoImage(originalFilename, request);
    }

    // 가게 이미지 삭제하기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "가게 이미지 삭제했습니다.")
    })
    @ApiOperation(value = "가게 이미지 삭제하기")
    @DeleteMapping("/deleteShopImage")
    public ResponseEntity<String> deleteShopImage(String originalFilename, HttpServletRequest request) {
        return shopService.deleteShopImage(originalFilename, request);
    }


}
