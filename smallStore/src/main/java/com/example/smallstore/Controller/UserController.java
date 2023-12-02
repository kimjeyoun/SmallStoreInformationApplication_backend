package com.example.smallstore.Controller;

import com.example.smallstore.Dto.ResponseDto;
import com.example.smallstore.Dto.Shop.SearchShopResponse;
import com.example.smallstore.Dto.User.*;
import com.example.smallstore.Dto.User.SMS.*;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    // 회원가입
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "회원가입 성공")
            , @ApiResponse(code = 401, message = "회원가입 실패/중복된 아이디 존재")
    })
    @ApiOperation(value = "유저 회원가입", response = ResponseDto.class)
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> userSignUp(@RequestBody UserSignupRequest userSignupRequest, HttpServletResponse response, HttpServletRequest request) {
        return userService.signup(userSignupRequest, response, request);
    }

    // 로그인
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 400, message = "로그인 실패/id 없음 혹은 비번 틀림 혹은 2차 인증 안됨")
    })
    @ApiOperation(value = "유저 로그인", response = ResponseDto.class)
    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse response, HttpServletRequest request) {
        return userService.login(userLoginRequest, response, request);
    }

    // 카카오 로그인
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "카카오 로그인 성공(user를 보냄)"),
            @ApiResponse(code = 201, message = "카카오 로그인 실패/회원가입 필요(userMap(email(id), nickname(null 이 있을수도있음)를 보냄)"),
            @ApiResponse(code = 400, message = "카카오 로그인 실패/카카오 api에서 문제 생김")
    })
    @ApiOperation(value = "유저 카카오 로그인")
    @GetMapping("/kakaoLogin")
    public ResponseEntity<User> userKakaoLogin(@RequestParam String code, HttpServletResponse response, HttpServletRequest request) throws ParseException {
        return userService.kakaoLogin(code, response, request);
    }

    // 마이페이지 수정
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "마이페이지 수정 성공")
    })
    @ApiOperation(value = "유저 정보 수정", response = ResponseDto.class)
    @PutMapping("/mypage")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userUpdateRequest);
    }

    // 로그아웃
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "로그아웃했습니다.")
    })
    @ApiOperation(value = "유저 로그아웃", response = ResponseDto.class)
    @GetMapping("/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    // 탈퇴
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "탈퇴 성공")
            , @ApiResponse(code = 400, message = "탈퇴 실패/비번 틀림")
    })
    @ApiOperation(value = "유저 탈퇴", response = ResponseDto.class)
    @DeleteMapping()
    public ResponseEntity<ResponseDto> delete(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        return userService.deleteUser(userDeleteRequest, request);
    }

    // 2차 인증 실행
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "2차 인증 문자 보내기 성공"),
            @ApiResponse(code = 400, message = "2차 인증 문자 보내기 실패/회원가입이 제대로 되지 않음")
    })
    @ApiOperation(value = "2차 인증 문자 보내기", notes = "회원가입 후 2차 인증이 이루어져야합니다.")
    @PostMapping("/sms/confirmed")
    public ResponseEntity<ResponseDto> secondConfirmed(@RequestBody UserConfirmedRequest userConfirmedRequest){
        return userService.secondConfirmed(userConfirmedRequest);
    }

    // 비밀번호 찾기 실행
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "비밀번호 찾기 문자 보내기 성공"),
            @ApiResponse(code = 400, message = "비밀번호 찾기 실패/id 혹은 전화번호가 없음.")
    })
    @ApiOperation(value = "비밀번호 찾기 문자 보내기", response = ResponseDto.class)
    @PostMapping("/sms/findPW")
    public ResponseEntity<ResponseDto> findPW(@RequestBody FindPWRequest findPWRequest){
        return userService.findPW(findPWRequest);
    }

    // 인증 확인 및 db update(2차 인증의 경우만)
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "인증 성공(2차 인증의 경우만 db update)"),
            @ApiResponse(code = 400, message = "인증 실패/인증 시간 지남."),
            @ApiResponse(code = 401, message = "인증 실패/코드 오류")
    })
    @ApiOperation(value = "인증 확인", response = ResponseDto.class)
    @PostMapping("/sms/verify")
    public ResponseEntity<ResponseDto> verifyCode(@RequestBody SMSVerifyRequest smsVerifyRequest){
        return userService.verifyCode(smsVerifyRequest);
    }

    // 비밀번호 변경
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "비밀번호 변경 성공"),
            @ApiResponse(code = 400, message = "비번 변경 실패/카카오로그인이라 비번 변경 불가")
    })
    @ApiOperation(value = "비밀번호 변경", response = ResponseDto.class)
    @PutMapping("/updatePW")
    public ResponseEntity<ResponseDto> updatePW(@RequestBody UpdatePWRequest updatePWRequest){
        return userService.updatePW(updatePWRequest);
    }

    // 가게 찜 목록 관리(추가, 삭제)
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "true = 추가 / false = 삭제")
    })
    @ApiOperation(value = "찜 목록 관리(추가, 삭제)")
    @PostMapping("/manageWishList")
    public boolean manageWishList(@RequestBody UserAddWishListRequest userAddWishListRequest){
        return userService.addWishList(userAddWishListRequest);
    }

    // 가게 찜 목록 보여주기
    @ApiResponses( value ={
            @ApiResponse(code = 200, message = "찜 목록")
    })
    @ApiOperation(value = "찜 목록 보여주기")
    @GetMapping("/showWishList")
    public ResponseEntity<List<SearchShopResponse>> showWishList(@RequestBody UserAddWishListRequest userAddWishListRequest){
        return userService.showWishList(userAddWishListRequest);
    }

}
