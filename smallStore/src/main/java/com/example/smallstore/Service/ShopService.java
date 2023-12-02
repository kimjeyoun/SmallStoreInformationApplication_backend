package com.example.smallstore.Service;

import com.example.smallstore.Dto.ResponseDto;
import com.example.smallstore.Dto.Shop.SearchShopRequest;
import com.example.smallstore.Dto.Shop.SearchShopResponse;
import com.example.smallstore.Dto.Shop.ShopNumberCheckRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import com.example.smallstore.Entity.Category;
import com.example.smallstore.Entity.Shop;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Repository.CategoryRepository;
import com.example.smallstore.Repository.ShopRepository;
import com.example.smallstore.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import okhttp3.*;
import okhttp3.MediaType;
import org.json.simple.JSONArray;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final S3Serivce s3Serivce;
    private final GpsService getGpsService;

    @Value("${shopNum.api}")
    private String api;
    @Value("${shopNum.serviceKey}")
    private String serviceKey ;

    // 사업자 등록 번호 확인
    public ResponseEntity numberCheck(ShopNumberCheckRequest shopNumberCheckRequest) {
        String result;
        String apiUrl = api+serviceKey;
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject requestBody=new JSONObject();
        requestBody.put("b_no",shopNumberCheckRequest.getB_no());
        RequestBody body = RequestBody.create(requestBody.toString(),mediaType);
        Request request = new Request.Builder()
                .url(apiUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        //결과 탐색
        try {
            Response response = client.newCall(request).execute();
            JSONArray data = (JSONArray) UtilService.stringToJson(response.body().string()).get("data");
            JSONObject data2 = (JSONObject) data.get(0);
            result = (String) data2.get("b_stt_cd");
            if(result.equals("01")) {
                return ResponseEntity.ok(ResponseDto.successRes(200, "사업자 번호 인증 성공"));
            }
            return ResponseEntity.badRequest().body(ResponseDto.failRes(400, "사업자 번호 인증 실패"));
        } catch (Exception e) {
            UtilService.ExceptionValue(e.getMessage(), String.class);
            return ResponseEntity.badRequest().body(ResponseDto.failRes(400, "코드 실패"));
        }
    }

    // 가게 등록
    public ResponseEntity regist(ShopRegisterRequest shopRegisterRequest) throws ParseException {
        User user = userRepository.findById(shopRegisterRequest.getId()).orElseThrow();
        Category category = categoryRepository.findByNum(shopRegisterRequest.getCategoryNum()).orElseThrow();
        List gpsList = this.getGps(shopRegisterRequest.getShopAddress());
        Shop shop = new Shop(shopRegisterRequest, user, category, gpsList);
        shopRepository.save(shop);
        return ResponseEntity.ok(ResponseDto.successRes(200, "가게 등록 성공"));
    }

    // 가게 보여주기
    public ResponseEntity showShop(SearchShopRequest searchShopRequest){
        // 10km 주위 반경 가게를 보여줌.
        List<Shop> shop = shopRepository.findShopsWithinDistance(Double.valueOf(searchShopRequest.getUserLng()),Double.valueOf(searchShopRequest.getUserLat()),Double.valueOf(10000));
        List<SearchShopResponse> shopList = shop.stream()
                .map(data -> new SearchShopResponse(data))
                .collect(Collectors.toList());

        if(shopList.isEmpty()){
            return ResponseEntity.badRequest().body(ResponseDto.failRes(400, "가게 데이터 불러오지 못함."));
        }
        return ResponseEntity.ok(ResponseDto.res(200, "가게 보여줌.", shopList));
    }

    // 검색
    public ResponseEntity searchShop(SearchShopRequest searchShopRequest){
        List<Shop> shop = shopRepository.findAllByShopNameContaining("%"+searchShopRequest.getKeyword()+"%");
        List<SearchShopResponse> shopList = shop.stream()
                .map(data -> new SearchShopResponse(data))
                .collect(Collectors.toList());

        if(shopList.isEmpty()){
            return ResponseEntity.badRequest().body(ResponseDto.failRes(400, "검색에 대한 값이 없음."));
        }
        return ResponseEntity.ok(ResponseDto.res(200, "검색 후 가게 보여줌.", shopList));
    }

    // 로고 이미지 저장하기
    public ResponseEntity saveLogoImg(MultipartFile fileName, HttpServletRequest request){
        if(!s3Serivce.uploadImage(fileName)){
            return ResponseEntity.badRequest().body(ResponseDto.failRes(400, "로고 이미지 저장 못함."));
        }
        User user = userService.findUserByToken(request);
        Shop shop = shopRepository.findByUser(user).orElseThrow();
        // db에 로고 파일 이름 저장
        shop.setShopLogo(fileName.getOriginalFilename());

        return ResponseEntity.ok(ResponseDto.successRes(200, "로고 이미지 저장함."));
    }

    // 가게 이미지 저장하기
    public ResponseEntity saveShopImg(MultipartFile fileName, HttpServletRequest request){
        if(!s3Serivce.uploadImage(fileName)){
            return ResponseEntity.badRequest().body(ResponseDto.failRes(400, "가게 이미지 저장 못함."));
        }
        User user = userService.findUserByToken(request);
        Shop shop = shopRepository.findByUser(user).orElseThrow();
        // db에 로고 파일 이름 저장
        shop.setShopPicture(fileName.getOriginalFilename());

        return ResponseEntity.ok(ResponseDto.successRes(200, "가게 이미지 저장함."));
    }

    // 이미지 가져오기
    public ResponseEntity downloadImg(String originalFilename){
        //String imgUrl = s3Serivce.downloadImage(originalFilename);

        // return ResponseEntity.ok(ResponseDto.res(200, "이미지 가져옴.", imgUrl));
        return ResponseEntity.ok(ResponseDto.res(200, "이미지 가져옴.", originalFilename));
    }

    // 로고 이미지 삭제하기
    public ResponseEntity deleteLogoImage(String originalFilename, HttpServletRequest request){
        //s3Serivce.deleteImage(originalFilename);
        User user = userService.findUserByToken(request);
        Shop shop = shopRepository.findByUser(user).orElseThrow();
        shop.setShopLogo("");

        return ResponseEntity.ok(ResponseDto.successRes(200, "로고 이미지 삭제함."));
    }

    // 가게 이미지 삭제하기
    public ResponseEntity deleteShopImage(String originalFilename, HttpServletRequest request){
        //s3Serivce.deleteImage(originalFilename);
        User user = userService.findUserByToken(request);
        Shop shop = shopRepository.findByUser(user).orElseThrow();
        shop.setShopPicture("");

        return ResponseEntity.ok(ResponseDto.successRes(200, "가게 이미지 삭제함."));
    }

    // lat, lng를 주소로 구하기
    public String getAddress(String lat, String lng) throws ParseException {
        String address = getGpsService.changeToJSONPost(getGpsService.getAddress(lat, lng));
        return address;
    }

    // 주소로 lat, lng 구하기
    public List getGps(String address) throws ParseException {
        List list = getGpsService.changeToJSONGet(getGpsService.getGps(address));
        return list;
    }

}
