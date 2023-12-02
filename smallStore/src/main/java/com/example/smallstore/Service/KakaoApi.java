package com.example.smallstore.Service;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoApi {
    @Value("${kakao.apiKey}")
    String kakaoApiKey;
    @Value("${kakao.getRedirectUrl}")
    String kakaoRedirectUrl;
    @Value("${kakao.getTokenUrl}")
    String kakaoGetTokenUrl;
    @Value("${kakao.getUserInfoUrl}")
    String getUserInfoUrl;

    private final RestTemplate restTemplate;

    public Map getToken(String code) throws ParseException {
        String access_Token = "";
        String refresh_Token = "";
        String refresh_token_expires_in = "";
        String expires_in = "";

        // 1. header 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        // 2. body 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); //고정값
        params.add("client_id", kakaoApiKey);
        params.add("redirect_uri", kakaoRedirectUrl);
        params.add("code", code);

        // 3. header + body
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

        // 4. http 요청하기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                kakaoGetTokenUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        if(response.getStatusCode().equals(HttpStatus.OK)){
            //Response 데이터 파싱
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
            access_Token = String.valueOf(jsonObj.get("access_token"));
            expires_in = String.valueOf(jsonObj.get("expires_in"));
            refresh_Token = String.valueOf(jsonObj.get("refresh_token"));
            refresh_token_expires_in = String.valueOf(jsonObj.get("refresh_token_expires_in"));
        }
        Map<String, Object> tokenMap = new HashMap<String, Object>();
        tokenMap.put("access_Token", access_Token);
        tokenMap.put("expires_in", expires_in);
        tokenMap.put("refresh_Token", refresh_Token);
        tokenMap.put("refresh_token_expires_in", refresh_token_expires_in);
        return tokenMap;
    }

    public Map getUserInfo(Map token){
        String email = "";
        String nickname = "";

        //access_token을 이용하여 사용자 정보 조회
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.get("access_Token").toString());

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(getUserInfoUrl, HttpMethod.GET, request, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JsonObject jsonObject = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
            JsonObject kakaoAccount = jsonObject.getAsJsonObject("kakao_account");
            JsonObject profile = kakaoAccount.getAsJsonObject("profile");
            email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            if(!profile.getAsJsonObject().get("nickname").getAsString().isEmpty()){
                nickname = profile.getAsJsonObject().get("nickname").getAsString();
            }
        } else {
            throw new RuntimeException("Failed to get user info from Kakao API!");
        }
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("email", email);
        userMap.put("nickname", nickname);
        return userMap;
    }

    public Map reissuedToken(String refreshToken) throws ParseException {
        String access_Token = "";
        String refresh_Token = "";
        String refresh_token_expires_in = "";
        String expires_in = "";

        // 1. header 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

        // 2. body 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token"); //고정값
        params.add("client_id", kakaoApiKey);
        params.add("refresh_token", refreshToken);

        // 3. header + body
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);

        // 4. http 요청하기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                kakaoGetTokenUrl,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        if(response.getStatusCode().equals(HttpStatus.OK)){
            //Response 데이터 파싱
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
            access_Token = String.valueOf(jsonObj.get("access_token"));
            expires_in = String.valueOf(jsonObj.get("expires_in"));
            refresh_Token = String.valueOf(jsonObj.get("refresh_token"));
            refresh_token_expires_in = String.valueOf(jsonObj.get("refresh_token_expires_in"));
        }
        Map<String, Object> tokenMap = new HashMap<String, Object>();
        tokenMap.put("access_Token", access_Token);
        tokenMap.put("expires_in", expires_in);
        tokenMap.put("refresh_Token", refresh_Token);
        tokenMap.put("refresh_token_expires_in", refresh_token_expires_in);
        return tokenMap;
    }


}
