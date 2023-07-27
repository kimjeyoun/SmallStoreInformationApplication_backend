package com.example.smallstore.Entity;

import com.example.smallstore.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long num;

    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String address;

    @Column(nullable = false)
    private String nickname;

    // 이메일 2차 인증
    @Column(nullable = false)
    @Type(type = "true_false")
    private boolean emailConfirmed;

    // 유저 권한
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

}
