package com.kiwoom.application.login.service;

import com.kiwoom.application.dto.LoginRequest;
import com.kiwoom.application.entity.UserInfo;
import com.kiwoom.application.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserInfoRepository userInfoRepository;

    public String login(LoginRequest request) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String userId = request.getLoginId();
        String password = request.getPassword();

        List<UserInfo> result = userInfoRepository.findByUserId(userId);

        if(result.isEmpty()) {
            throw new Exception("로그인 실패");
        }

        if(result.size() > 1){
            throw new Exception("로그인 실패");
        }

        if( !encoder.matches(password,result.get(0).getPassword())){
            throw new Exception("로그인 실패");
        }


        return result.get(0).getNickName();
    }

//    private void passwordMaker(){
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        String rawPassword = "kingkid@753";
//        String hashedPassword = encoder.encode(rawPassword);
//
//        System.out.println("hashed: " + hashedPassword);
//
//        boolean matches = encoder.matches("mySecret123", hashedPassword);
//        System.out.println("matches: " + matches); // true
//    }
}
