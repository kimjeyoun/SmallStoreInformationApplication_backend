package com.example.smallstore.Entity;

import com.example.smallstore.Dto.User.SMS.UpdatePWRequest;
import com.example.smallstore.Dto.User.UserAddWishListRequest;
import com.example.smallstore.enums.LoginType;
import com.example.smallstore.enums.UserRole;
import com.example.smallstore.Dto.User.UserUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @ApiModelProperty(value = "id", example = "test")
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @ApiModelProperty(value = "phone", example = "01012345678")
    @Column(nullable = false)
    private String phone;

    @ApiModelProperty(value = "password", example = "test")
    @Column(nullable = false)
    private String password;

    @ApiModelProperty(value = "address", example = "경기도 군포시")
    @Column(nullable = false)
    private String address;

    @ApiModelProperty(value = "nickname", example = "test")
    @Column(nullable = true)
    private String nickname;

    // 이메일 2차 인증
    @ApiModelProperty(value = "2차 인증 확인", example = "T/F")
    @Column(nullable = false)
    @Type(type = "true_false")
    private boolean secondConfirmed;

    @ApiModelProperty(value = "찜 목록", example = " ['1','2','4'] ")
    @ElementCollection
    private List<Long> wishList;

    // 유저 권한
    @ApiModelProperty(value = "유저 권한", example = "USER/SHOPOWNER")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_role")
    private UserRole userRole;

    // 로그인 타입
    @ApiModelProperty(value = "로그인 타입", example = "kakao/local")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginType loginType;

    public void update(UserUpdateRequest userUpdateRequest) {
        this.phone = userUpdateRequest.getPhone();
        this.address = userUpdateRequest.getAddress();
        this.nickname = userUpdateRequest.getNickname();
    }

    public void updatePW(UpdatePWRequest updatePWRequest) {
        this.phone = updatePWRequest.getPhone();
        this.password = updatePWRequest.getPassword();
        this.loginType = updatePWRequest.getLoginType();
    }

    public void updateWishList(UserAddWishListRequest userAddWishListRequest) {
        this.wishList.add(userAddWishListRequest.getNum());
    }

}
