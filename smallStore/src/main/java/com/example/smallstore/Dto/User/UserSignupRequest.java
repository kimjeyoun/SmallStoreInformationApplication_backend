package com.example.smallstore.Dto.User;

import com.example.smallstore.Entity.User;
import com.example.smallstore.enums.UserRole;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class UserSignupRequest {
    private String id,email, password, address, nickname;
    private UserRole userRole;
    private boolean emailConfirmed;

    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .address(address)
                .userRole(userRole)
                .nickname(nickname)
                .emailConfirmed(emailConfirmed)
                .build();
    }
}
