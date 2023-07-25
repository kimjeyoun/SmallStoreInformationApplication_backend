package com.example.smallstore.Service;

import com.example.smallstore.Dto.User.UserLoginRequest;
import com.example.smallstore.Entity.User;
import com.example.smallstore.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 로그인
    public boolean login(UserLoginRequest userLoginRequest){
        if(!userRepository.existsById(userLoginRequest.getId())){ // 이메일이 존재하지 않으면
            return false;
        }
        User user = userRepository.findById(userLoginRequest.getId()).orElseThrow();
        if(!user.isEmailConfirmed()){
            return false;
        }

        // 로그인 성공 시
        return true;
    }
}
