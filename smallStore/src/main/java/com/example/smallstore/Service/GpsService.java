package com.example.smallstore.Service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class GpsService {
    @Value("${kakao.apiKey}")
    private String apiKey;

    @Value("${kakao.getGPS}")
    private String getUrl ;

    @Value("${kakao.getAddress}")
    private String postUrl ;

    public String getGps(String stringAddress){
        String jsonString = null;
        try {
            stringAddress = URLEncoder.encode(stringAddress, "UTF-8");
            String addr = getUrl + "?query=" + stringAddress;
            URL url = new URL(addr);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
            BufferedReader json = null;
            json = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer docJson = new StringBuffer();
            String line;
            while ((line = json.readLine()) != null) {
                docJson.append(line);
            }
            jsonString = docJson.toString();
            json.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public String getAddress(String lat, String lng){
        String stringJson = null;
        try {
            lat = URLEncoder.encode(lat, "UTF-8");
            lng = URLEncoder.encode(lng, "UTF-8");
            String addr = postUrl+"?x=" +lng +"&y="+lat;

            URL url = new URL(addr);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
            BufferedReader json = null;
            json = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer docJson = new StringBuffer();
            String line;
            while ((line = json.readLine()) != null) {
                docJson.append(line);
            }
            stringJson = docJson.toString();
            json.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringJson;
    }

    public ArrayList changeToJSONGet(String jsonString) throws ParseException {
        ArrayList<Float> array = new ArrayList<Float>();
        JSONParser parser = new JSONParser();
        JSONObject document = (JSONObject)parser.parse(jsonString);
        JSONArray jsonArray = (JSONArray) document.get("documents");
        JSONObject position = (JSONObject)jsonArray.get(0);
        float lon = Float.parseFloat((String) position.get("x"));
        float lat = Float.parseFloat((String) position.get("y"));
        array.add(lat);
        array.add(lon);
        return array;
    }

    public String changeToJSONPost(String jsonString) throws ParseException {
        String addressString = null;
        JSONParser parser = new JSONParser();
        JSONObject document = (JSONObject)parser.parse(jsonString);
        JSONArray jsonArray = (JSONArray) document.get("documents");
        JSONObject position = (JSONObject) jsonArray.get(0);
        JSONObject roadAddress = (JSONObject) position.get("road_address");
        addressString = (String) roadAddress.get("address_name");
        return addressString;
    }
}
