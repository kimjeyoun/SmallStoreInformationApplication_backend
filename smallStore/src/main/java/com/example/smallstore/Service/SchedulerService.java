package com.example.smallstore.Service;

import com.example.smallstore.Repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SchedulerService {
    private final TokenRepository tokenRepository;

    // Token 만료 시 데이터 삭제
    @Scheduled(fixedRate = 3600000) // 일정한 시간 간격 혹은 특정 시간에 코드가 실행되도록 설정
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();

        tokenRepository.deleteByExpirationDate(now);
    }
}
