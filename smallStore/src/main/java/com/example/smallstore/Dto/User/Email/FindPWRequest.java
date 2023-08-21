package com.example.smallstore.Dto.User.Email;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class FindPWRequest {
    private String id, email;
}
