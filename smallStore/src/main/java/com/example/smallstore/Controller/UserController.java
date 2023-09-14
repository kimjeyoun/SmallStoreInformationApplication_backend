package com.example.smallstore.Controller;

import com.example.smallstore.Dto.User.*;
import com.example.smallstore.Dto.User.Email.*;
import com.example.smallstore.Service.UserService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final ChatController chatController;

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
    public void userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response, HttpServletRequest request, Model model) {
        userService.login(userLoginRequest, response, request);
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

    // 2차 인증 이메일 보내기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "2차 인증 메일을 보냈습니다.")
            , @ApiResponse(code = 401, message = "2차 인증이 완료된 유저입니다.")
    })
    @ApiOperation(value = "2차 인증 이메일 보내기")
    @PostMapping("/email")
    public ResponseEntity check(@RequestBody EmailRequest emailRequest) throws MessagingException {
        return userService.sendEmail(emailRequest.getEmail());
    }

    // 이메일 인증 코드 확인
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "2차 인증 성공하였습니다.")
    })
    @ApiOperation(value = "이메일 인증")
    @PostMapping("/email/emailVerify")
    public ResponseEntity verify(@RequestBody EmailVerifyRequest emailVerifyRequest) throws MessagingException {
        return userService.verifyEmail(emailVerifyRequest);
    }

    // 비밀번호 찾기 이메일 보내기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "비밀번호 찾기 메일이 보내졌습니다.")
            , @ApiResponse(code = 400, message = "등록된 이메일과 다릅니다.")
    })
    @ApiOperation(value = "비밀번호 찾기 이메일 보내기")
    @PostMapping("/email/findPW")
    public ResponseEntity findPW(@RequestBody FindPWRequest findPWRequest) throws MessagingException {
        return userService.findPW(findPWRequest);
    }

    // 비밀번호 변경
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "비밀번호 변경이 완료되었습니다.")
    })
    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/email/updatePW")
    public ResponseEntity updatePW(@RequestBody UpdatePWRequest updatePWRequest){
        return userService.updatePW(updatePWRequest);
    }
}
