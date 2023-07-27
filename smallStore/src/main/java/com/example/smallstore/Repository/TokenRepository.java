package com.example.smallstore.Repository;

import com.example.smallstore.Dto.User.RefreshToken.TokenResponse;
import com.example.smallstore.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<TokenResponse> findByRefreshToken(String refreshToken);

    @Modifying // 데베 수정 작업 수행하는 메서드
    @Query("DELETE FROM Token t WHERE t.refreshToken = :refreshToken")
    void deleteByRefreshToken(@Param("refreshToken")String refreshToken);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.expirationDate < :now")
    void deleteByExpirationDate(@Param("now") LocalDateTime now);
}
