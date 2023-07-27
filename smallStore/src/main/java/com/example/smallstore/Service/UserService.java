package com.example.smallstore.Service;

import com.example.smallstore.Dto.User.Email.EmailVerifyRequest;
import com.example.smallstore.Dto.User.UserDeleteRequest;
import com.example.smallstore.Dto.User.UserLoginRequest;
import com.example.smallstore.Dto.User.UserSignupRequest;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Error.ErrorException;
import com.example.smallstore.JWT.JwtTokenProvider;
import com.example.smallstore.Repository.EmailAuthRepository;
import com.example.smallstore.Repository.UserRepository;
import com.example.smallstore.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.smallstore.Error.ErrorCode.ACCESS_DENIED_EXCEPTION;
import static com.example.smallstore.Error.ErrorCode.NOT_ALLOW_WRITE_EXCEPTION;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final EmailAuthRepository emailAuthRepository;
    private final RefreshTokenService refreshTokenService;

    // 토큰 헤더에 저장
    public void setJwtTokenInHeader(String id, HttpServletResponse response, HttpServletRequest request) {
        UserRole userRole = userRepository.findById(id).orElseThrow().getUserRole();

        String accessToken = jwtTokenProvider.createAccessToken(id, userRole);
        String refreshToken = jwtTokenProvider.createRefreshToken(id, userRole);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        String ip = this.getClientIp(request);
        refreshTokenService.saveRefreshToken(id, ip, refreshToken);
    }

    // 회원가입
    public ResponseEntity signup(UserSignupRequest userSignupRequest, HttpServletResponse response, HttpServletRequest request) {
        // 아이디가 있으면 에러 던짐.
        if (userRepository.existsById(userSignupRequest.getId())) {
            throw new ErrorException("401", ACCESS_DENIED_EXCEPTION);
        }
        // 비밀번호 저장
        userSignupRequest.setPassword(passwordEncoder.encode(userSignupRequest.getPassword()));
        // 2차 인증 이메일 해야함.
        userSignupRequest.setEmailConfirmed(false);
        userRepository.save(userSignupRequest.toEntity());
        this.setJwtTokenInHeader(userSignupRequest.getId(), response, request);
        return ResponseEntity.ok("회원가입 성공."+userSignupRequest);
    }

    // 로그인
    public ResponseEntity login(UserLoginRequest userLoginRequest, HttpServletResponse response, HttpServletRequest request){
        if(!userRepository.existsById(userLoginRequest.getId())){ // id가 존재하지 않으면
            return ResponseEntity.badRequest().body("이메일이 없습니다. 다시 시도하세요.");
        }

        User user = userRepository.findById(userLoginRequest.getId()).orElseThrow();

        if(!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())){ // 비밀번호 틀리면
            return ResponseEntity.badRequest().body("비밀번호가 틀렸습니다. 다시 시도하세요.");
        }
        if(!user.isEmailConfirmed()){
            return ResponseEntity.badRequest().body("2차 인증이 되지 않았습니다. 다시 시도하세요.");
        }
        this.setJwtTokenInHeader(userLoginRequest.getId(), response, request);

        // 로그인 성공 시
        return ResponseEntity.ok(user.getNickname()+"님 환영합니다.");
    }

    // 로그아웃
    public void logout(HttpServletRequest request) {
        refreshTokenService.deleteToken(jwtTokenProvider.resolveRefreshToken(request));
        jwtTokenProvider.expireToken(jwtTokenProvider.resolveAccessToken(request));
    }

    // 탈퇴
    public ResponseEntity deleteUser(UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        User user = this.findUserByToken(request);
        if(passwordEncoder.matches(userDeleteRequest.getPassword(), user.getPassword())){
            userRepository.deleteById(user.getId());
            refreshTokenService.deleteToken(jwtTokenProvider.resolveRefreshToken(request));
            return ResponseEntity.ok("탈퇴가 완료되었습니다.");
        }
        return ResponseEntity.badRequest().body("비밀번호가 맞지 않습니다. 다시 시도하세요.");
    }

    // 2차 인증 이메일 보냄
    public ResponseEntity sendEmail(String email) throws MessagingException {
        User user = userRepository.findByEmail(email).orElseThrow();
        if(user.isEmailConfirmed()){
            throw new ErrorException("2차 인증이 완료된 사용자입니다.", NOT_ALLOW_WRITE_EXCEPTION);
        }
        emailService.saveDB(email);
        return ResponseEntity.ok("이메일이 정상적으로 보내졌습니다.");
    }

    // 2차 인증 이메일 인증
    public ResponseEntity verifyEmail(EmailVerifyRequest emailVerifyRequest){
        emailService.verifyEmail(emailVerifyRequest.getEmail(), emailVerifyRequest.getRandomCode());
        User user = userRepository.findByEmail(emailVerifyRequest.getEmail()).orElseThrow();
        user.setEmailConfirmed(true);
        userRepository.save(user);
        emailAuthRepository.deleteByEmail(user.getEmail());

        return ResponseEntity.ok("2차 인증이 완료되었습니다. 로그인이 가능합니다.");
    }

    // 토큰에서 정보 가져오기
    public User findUserByToken(HttpServletRequest request) {
        String id = jwtTokenProvider.getUserId(jwtTokenProvider.resolveAccessToken(request));
        User user = userRepository.findById(id).orElseThrow();
        return user;
    }

    // ip 가져오기
    public static String getClientIp(HttpServletRequest request) {
        String clientIp = null;
        boolean isIpInHeader = false;

        List<String> headerList = new ArrayList<>();
        headerList.add("X-Forwarded-For");
        headerList.add("HTTP_CLIENT_IP");
        headerList.add("HTTP_X_FORWARDED_FOR");
        headerList.add("HTTP_X_FORWARDED");
        headerList.add("HTTP_FORWARDED_FOR");
        headerList.add("HTTP_FORWARDED");
        headerList.add("Proxy-Client-IP");
        headerList.add("WL-Proxy-Client-IP");
        headerList.add("HTTP_VIA");
        headerList.add("IPV6_ADR");

        for (String header : headerList) {
            clientIp = request.getHeader(header);
            if (StringUtils.hasText(clientIp) && !clientIp.equals("unknown")) {
                isIpInHeader = true;
                break;
            }
        }
        if (!isIpInHeader) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
}
