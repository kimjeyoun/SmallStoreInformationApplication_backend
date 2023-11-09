package com.example.smallstore.Repository;

import com.example.smallstore.Entity.SMSAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SMSAuthRepository extends JpaRepository<SMSAuth, Long> {
    Optional<SMSAuth> findByPhone(String phone);

    boolean existsByPhone(String phone);

    @Modifying
    @Transactional
    @Query("DELETE FROM SMSAuth e WHERE e.phone = :phone")
    void deleteByPhone(@Param("phone") String phone);
}
