package com.example.smallstore.Service;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoApi {
    @Value("${spring.security.oauth.kakao.client-id}")
    String kakaoClientID;

    @Value("${spring.security.oauth.kakao.url.rediret-api}")
    String kakaoRedirectUri;

    private final RestTemplate restTemplate;

    public String getAceessToken(String code){
        String access_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        try{
            // HTTP 프로토콜 사용하여 서버와 통신하는데 사용되는 클래스(원격 서버와 연결하여 데이터 전송, 받음)
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+kakaoClientID); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri="+kakaoRedirectUri); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            if(conn.getResponseCode() == 200){ // 응답 성공
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String result = "";
                while ((line = br.readLine()) != null) {
                    result += line;
                }
                //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                access_Token = element.getAsJsonObject().get("access_token").getAsString();
                br.close();
            } else {
                access_Token = "실패";
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    public String getUserInfo(String accessToken){
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String email = "";

        //access_token을 이용하여 사용자 정보 조회
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(reqURL, HttpMethod.GET, request, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            JsonObject jsonObject = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
            JsonObject kakaoAccount = jsonObject.getAsJsonObject("kakao_account");
            email = kakaoAccount.getAsJsonObject().get("email").getAsString();
        } else {
            throw new RuntimeException("Failed to get user info from Kakao API!");
        }
        return email;
    }


}
