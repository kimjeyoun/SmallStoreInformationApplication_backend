package com.example.smallstore.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class Token {
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String refreshToken;

    @Column(nullable = false)
    private String ip;

    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime expirationDate;
}
