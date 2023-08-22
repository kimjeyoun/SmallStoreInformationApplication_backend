package com.example.smallstore.Controller;

import com.example.smallstore.Dto.User.*;
import com.example.smallstore.Dto.User.Email.*;
import com.example.smallstore.Service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    // 회원가입
    @ApiOperation(value = "유저 회원가입")
    @PostMapping("/signup")
    public ResponseEntity userSignUp(@RequestBody UserSignupRequest userSignupRequest, HttpServletResponse response, HttpServletRequest request) {
        return userService.signup(userSignupRequest, response, request);
    }

    // 로그인
    @ApiOperation(value = "유저 로그인")
    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response, HttpServletRequest request) {
        return userService.login(userLoginRequest, response, request);
    }

    // ayZtHRXq_Ujlol1P1j6CHQBQ-yMhR_sOfpQQLJ-csQ8QSd-QTOO2leyBQy71JtENZ81Lewo9dBEAAAGKHZMSTg
    // 카카오 로그인
    @ApiOperation(value = "유저 카카오 로그인")
    @ResponseBody
    @GetMapping("/kakaoLogin")
    public ResponseEntity userKakaoLogin(@RequestParam String code) {
        System.out.println("code : " + code);
        return userService.kakaoLogin(code);
    }

    // 마이페이지 수정
    @ApiOperation(value = "유저 정보 수정")
    @PutMapping("/mypage")
    public ResponseEntity updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userUpdateRequest);
    }

    // 로그아웃
    @ApiOperation(value = "유저 로그아웃")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        userService.logout(request);
    }

    // 탈퇴
    @ApiOperation(value = "유저 탈퇴")
    @DeleteMapping()
    public ResponseEntity delete(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        return userService.deleteUser(userDeleteRequest, request);
    }

    // 2차 인증 이메일 보내기
    @ApiOperation(value = "2차 인증 이메일 보내기")
    @PostMapping("/email")
    public ResponseEntity check(@RequestBody EmailRequest emailRequest) throws MessagingException {
        return userService.sendEmail(emailRequest.getEmail());
    }

    // 이메일 인증 코드 확인
    @ApiOperation(value = "이메일 인증")
    @PostMapping("/email/emailVerify")
    public ResponseEntity verify(@RequestBody EmailVerifyRequest emailVerifyRequest) throws MessagingException {
        return userService.verifyEmail(emailVerifyRequest);
    }

    // 비밀번호 찾기 이메일 보내기
    @ApiOperation(value = "비밀번호 찾기 이메일 보내기")
    @PostMapping("/email/findPW")
    public ResponseEntity findPW(@RequestBody FindPWRequest findPWRequest) throws MessagingException {
        return userService.findPW(findPWRequest);
    }

    // 비밀번호 변경
    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/email/updatePW")
    public ResponseEntity updatePW(@RequestBody UpdatePWRequest updatePWRequest){
        return userService.updatePW(updatePWRequest);
    }
}
