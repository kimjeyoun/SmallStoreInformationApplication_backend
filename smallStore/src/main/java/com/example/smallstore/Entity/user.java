package com.example.smallstore.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class user {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_num;

    @Id
    @Column(unique = true, nullable = false)
    private String user_id;

    @Column(unique = true, nullable = false)
    private String user_email;

    @Column(nullable = false)
    private String user_password;

    @Column(unique = true, nullable = false)
    private String user_address;

    @Column(nullable = false)
    private String user_nickname;

    // 이메일 2차 인증
    @Column(nullable = false)
    @Type(type = "true_false")
    private boolean email_confirmed;

    // 유저 권한
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserRole userRole;
}
