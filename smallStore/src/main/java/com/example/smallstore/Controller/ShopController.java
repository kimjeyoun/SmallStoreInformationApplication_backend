package com.example.smallstore.Controller;

import com.example.smallstore.Dto.ResponseDto;
import com.example.smallstore.Dto.Shop.SearchShopRequest;
import com.example.smallstore.Dto.Shop.SearchShopResponse;
import com.example.smallstore.Dto.Shop.ShopNumberCheckRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import com.example.smallstore.Repository.ShopRepository;
import com.example.smallstore.Service.S3Serivce;
import com.example.smallstore.Service.ShopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/shop")
public class ShopController {
    private final ShopService shopService;
    private final ShopRepository shopRepository;
    private final S3Serivce s3Serivce;

    // 사업자 등록 번호 상태 확인(운영하고 있는지 확인)
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "사업자 번호 인증 성공")
            , @ApiResponse(code = 400, message = "사업자 번호 인증 실패")
    })
    @ApiOperation(value = "사업자등록번호 상태 확인")
    @PostMapping("/shopNumStatus")
    public ResponseEntity<ResponseDto> numberCheck(@RequestBody ShopNumberCheckRequest shopNumberCheckRequest) {
        return shopService.numberCheck(shopNumberCheckRequest);
    }

    // 가게 등록
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "가게 등록 성공")
    })
    @ApiOperation(value = "가게 등록")
    @PostMapping("/regist")
    public ResponseEntity<ResponseDto> shopSignup(@RequestBody ShopRegisterRequest shopRegisterRequest) throws ParseException {
        return shopService.regist(shopRegisterRequest);
    }

    // 가게 보여주기 (거리로)
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "가게 보여주기 성공")
    })
    @ApiOperation(value = "가게 보여주기", notes = "거리(10km)로 가게를 보여줌.")
    @GetMapping("/showShop")
    public ResponseEntity<List<SearchShopResponse>> showShop(@RequestBody SearchShopRequest searchShopRequest) {
        return shopService.showShop(searchShopRequest);
    }

    // 검색
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "가게 검색 성공")
    })
    @ApiOperation(value = "가게 검색", notes = "body 중 keyword 만 보내면 됨.")
    @GetMapping("/searchShop")
    public ResponseEntity<List<SearchShopResponse>> searchShop(@RequestBody SearchShopRequest searchShopRequest) {
        return shopService.searchShop(searchShopRequest);
    }

    // 로고 이미지 저장
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "로고 이미지 저장했습니다."),
            @ApiResponse(code = 400, message = "로고 이미지 저장하지 못했습니다.")
    })
    @ApiOperation(value = "로고 이미지 저장")
    @GetMapping("/saveLogoImg")
    public ResponseEntity<ResponseDto> saveLogoImg(MultipartFile fileName, HttpServletRequest request) {
        return shopService.saveLogoImg(fileName, request);
    }

    // 가게 이미지 저장
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "가게 이미지 저장했습니다."),
            @ApiResponse(code = 400, message = "가게 이미지 저장하지 못했습니다.")
    })
    @ApiOperation(value = "가게 이미지 저장")
    @GetMapping("/saveShopImg")
    public ResponseEntity<ResponseDto> saveShopImg(MultipartFile fileName, HttpServletRequest request) {
        return shopService.saveShopImg(fileName, request);
    }

    // 이미지 가져오기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "이미지 가져오기 성공했습니다.")
    })
    @ApiOperation(value = "이미지 가져오기")
    @GetMapping("/downloadLogoImg")
    public ResponseEntity<String> downloadImg(String originalFilename) {
        return shopService.downloadImg(originalFilename);
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
