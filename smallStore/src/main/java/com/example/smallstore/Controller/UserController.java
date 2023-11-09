package com.example.smallstore.Controller;

import com.example.smallstore.Dto.User.*;
import com.example.smallstore.Dto.User.Email.*;
import com.example.smallstore.Service.SMSService;
import com.example.smallstore.Service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final SMSService messageService;

    // 회원가입
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "회원가입 성공하였습니다.")
            , @ApiResponse(code = 401, message = "중복된 아이디가 있습니다.")
    })
    @ApiOperation(value = "유저 회원가입")
    @PostMapping("/signup")
    public ResponseEntity userSignUp(@RequestBody UserSignupRequest userSignupRequest, HttpServletResponse response, HttpServletRequest request) {
        return userService.signup(userSignupRequest, response, request);
    }

    // 로그인
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "로그인 성공하였습니다.")
            , @ApiResponse(code = 400, message = "이메일이 없거나 비밀번호를 틀리거나 2차 인증 이메일이 인증 되지 않았습니다.")
    })
    @ApiOperation(value = "유저 로그인")
    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response, HttpServletRequest request, Model model) {
        return userService.login(userLoginRequest, response, request);
    }

    // 마이페이지 수정
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "마이페이지 정보 수정했습니다.")
    })
    @ApiOperation(value = "유저 정보 수정")
    @PutMapping("/mypage")
    public ResponseEntity updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userUpdateRequest);
    }

    // 로그아웃
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "로그아웃했습니다.")
    })
    @ApiOperation(value = "유저 로그아웃")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        userService.logout(request);
    }

    // 탈퇴
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "탈퇴 성공하였습니다.")
            , @ApiResponse(code = 400, message = "비밀번호가 틀렸습니다.")
    })
    @ApiOperation(value = "유저 탈퇴")
    @DeleteMapping()
    public ResponseEntity delete(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        return userService.deleteUser(userDeleteRequest, request);
    }

    // sms 보내기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "문자를 보냈습니다.")
    })
    @ApiOperation(value = "문자 보내기")
    @PostMapping("/sms/send")
    public ResponseEntity sendSMS(@RequestParam String toNumber, String type){
        return messageService.sendMessage(toNumber, type);
    }

    // 인증 번호 확인
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "인증 완료했습니다."),
            @ApiResponse(code = 500, message = "인증 시간이 지나거나 인증 코드 오류")
    })
    @ApiOperation(value = "코드 인증하기")
    @PostMapping("/sms/verify")
    public ResponseEntity verifySMS(@RequestParam String toNumber, String user_randomCode){
        return messageService.verifySMS(toNumber, user_randomCode);
    }

    // 비밀번호 변경
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "비밀번호 변경이 완료되었습니다.")
    })
    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/sms/updatePW")
    public ResponseEntity updatePW(@RequestBody UpdatePWRequest updatePWRequest){
        return userService.updatePW(updatePWRequest);
    }

}
