package com.example.smallstore.Service;

import com.example.smallstore.Dto.User.RefreshToken.TokenResponse;
import com.example.smallstore.Entity.Token;
import com.example.smallstore.JWT.JwtTokenProvider;
import com.example.smallstore.Repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // refreshToken 저장
    public void saveRefreshToken(String id, String ip, String refreshToken){
        Date date = jwtTokenProvider.getExpireDate(refreshToken);
        Instant instant = date.toInstant();
        LocalDateTime expirationDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        tokenRepository.save(Token.builder()
                        .id(id)
                        .refreshToken(refreshToken)
                        .ip(ip)
                        .expirationDate(expirationDate)
                        .build());
    }

    // refreshToken 으로 값 가져오기
    public TokenResponse getValues(String refreshToken){
        TokenResponse tokenResponse = tokenRepository.findByRefreshToken(refreshToken).orElseThrow();
        return tokenResponse;
    }
    
    // refreshToken 삭제
    public void deleteToken(String refreshToken){
        tokenRepository.deleteByRefreshToken(refreshToken);
    }
    
    // refreshToken ip 확인 후 이메일 보내 사용자 확인
    
}
