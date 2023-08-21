package com.example.smallstore.Dto.User.Email;

import com.example.smallstore.Entity.EmailAuth;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class EmailSaveRequest {
    private String email, randomCode, type;

    public EmailAuth toEntity() {
        return EmailAuth.builder()
                .email(email)
                .randomCode(randomCode)
                .type(type)
                .build();
    }
}
