package com.example.smallstore.Controller;

import com.example.smallstore.Dto.User.UserLoginRequest;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Repository.UserRepository;
import com.example.smallstore.Service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 로그인
    @ApiOperation(value = "유저 로그인")
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        if(!userService.login(userLoginRequest)){
            return ResponseEntity.badRequest().body("로그인이 실패하였습니다. 다시 시도하세요.");
        }
        User user = userRepository.findById(userLoginRequest.getId()).orElseThrow();
        userService.login(userLoginRequest);

        return ResponseEntity.ok(user.getNickname()+"님 환영합니다.");
    }
}
