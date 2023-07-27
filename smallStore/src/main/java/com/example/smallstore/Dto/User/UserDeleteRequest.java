package com.example.smallstore.Dto.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data // get, set 둘 다 됨.
@RequiredArgsConstructor
public class UserDeleteRequest {
    private String id, password;
}
