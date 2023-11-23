package com.example.smallstore.Service;

import com.example.smallstore.Dto.ResponseDto;
import com.example.smallstore.Dto.Shop.ShopNumberCheckRequest;
import com.example.smallstore.Dto.Shop.ShopRegisterRequest;
import com.example.smallstore.Entity.Category;
import com.example.smallstore.Entity.Shop;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Repository.CategoryRepository;
import com.example.smallstore.Repository.ShopRepository;
import com.example.smallstore.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import okhttp3.*;
import okhttp3.MediaType;
import org.json.simple.JSONArray;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopService {
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Value("${shopNum.api}")
    String api;
    @Value("${shopNum.serviceKey}")
    String serviceKey ;

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
            return null;
        }
    }

    // 가게 등록
    public ResponseEntity regeist(ShopRegisterRequest shopRegisterRequest) {
        User user = userRepository.findById(shopRegisterRequest.getId()).orElseThrow();
        Category category = categoryRepository.findByNum(shopRegisterRequest.getCategoryNum()).orElseThrow();
        Shop shop = new Shop(shopRegisterRequest, user, category);
        shopRepository.save(shop);
        return ResponseEntity.ok(ResponseDto.successRes(200, "가게 등록 성공"));
    }
}
