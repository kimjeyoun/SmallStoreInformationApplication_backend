package com.example.smallstore.Repository;

import com.example.smallstore.Entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
    Optional<EmailAuth> findByNumber(String number);

    boolean existsByNumber(String number);

    @Modifying
    @Transactional
    @Query("DELETE FROM EmailAuth e WHERE e.number = :number")
    void deleteByNumber(@Param("number") String number);
}
