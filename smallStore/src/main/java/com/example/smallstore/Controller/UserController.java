package com.example.smallstore.Controller;

import com.example.smallstore.Dto.User.Email.EmailRequest;
import com.example.smallstore.Dto.User.Email.EmailVerifyRequest;
import com.example.smallstore.Dto.User.UserDeleteRequest;
import com.example.smallstore.Dto.User.UserLoginRequest;
import com.example.smallstore.Dto.User.UserSignupRequest;
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

    @ApiOperation(value = "2차 인증 이메일")
    @PostMapping("/email")
    public ResponseEntity check(@RequestBody EmailRequest emailRequest) throws MessagingException {
        return userService.sendEmail(emailRequest.getEmail());
    }

    // 2차 인증 이메일
    @ApiOperation(value = "이메일 인증")
    @PostMapping("/emailVerify")
    public ResponseEntity verify(@RequestBody EmailVerifyRequest emailVerifyRequest){
        return userService.verifyEmail(emailVerifyRequest);
    }
}
