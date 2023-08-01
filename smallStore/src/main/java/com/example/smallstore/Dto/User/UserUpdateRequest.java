package com.example.smallstore.Dto.User;

import com.example.smallstore.Entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserUpdateRequest {
    private String id,email,address,nickname;

    public User toEntity(){
        return User.builder()
                .email(email)
                .address(address)
                .nickname(nickname)
                .build();
    }
}
