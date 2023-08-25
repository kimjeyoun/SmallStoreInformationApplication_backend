package com.example.smallstore.Dto.User;

import com.example.smallstore.Entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserUpdateRequest {
    @ApiModelProperty(example = "test")
    private String id,email,nickname;
    @ApiModelProperty(example = "경기도 군포시")
    private String address;

    public User toEntity(){
        return User.builder()
                .email(email)
                .address(address)
                .nickname(nickname)
                .build();
    }
}
