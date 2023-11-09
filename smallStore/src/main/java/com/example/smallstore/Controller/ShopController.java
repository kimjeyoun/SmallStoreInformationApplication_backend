package com.example.smallstore.Controller;

import com.example.smallstore.Dto.Shop.S3.S3DownloadRequest;
import com.example.smallstore.Dto.Shop.S3.S3UploadRequest;
import com.example.smallstore.Dto.Shop.ShopNumberCheckRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import com.example.smallstore.Repository.ShopRepository;
import com.example.smallstore.Service.S3Serivce;
import com.example.smallstore.Service.ShopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @GetMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestBody S3UploadRequest s3UploadRequest, HttpServletRequest request) throws IOException {
        return s3Serivce.uploadImage(s3UploadRequest, request);
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadImage(@RequestBody S3DownloadRequest s3DownloadRequest) {
        return s3Serivce.downloadImage(s3DownloadRequest);
    }

}
