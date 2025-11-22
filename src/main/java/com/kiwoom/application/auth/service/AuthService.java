package com.kiwoom.application.auth.service;

import com.kiwoom.application.dto.LoginRequest;
import com.kiwoom.application.entity.UserInfo;
import com.kiwoom.application.jwt.JwtUtil;
import com.kiwoom.application.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

  private final JwtUtil jwtUtil;

  private final UserInfoRepository userInfoRepository;

  public UserInfo login(LoginRequest request) throws Exception {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    String userId = request.getLoginId();
    String password = request.getPassword();

    UserInfo result = userInfoRepository.findByUserId(userId);

    if (result == null) {
      throw new Exception("로그인 실패");
    }

    if (!encoder.matches(password, result.getPassword())) {
      throw new Exception("로그인 실패");
    }

    return result;
  }

  public String refresh(String refreshToken) throws Exception {
    if (refreshToken == null) {
      throw new Exception("로그인 실패");
    }

    UserInfo result = userInfoRepository.findOneByRefreshToken(refreshToken);

    if (result == null) {
      throw new Exception("로그인 실패");
    }

    if (!jwtUtil.validateToken(refreshToken)) {
      result.setRefreshToken(null);
      throw new Exception("로그인 실패");
    }

    String userId = jwtUtil.getUserId(refreshToken);

    return jwtUtil.createAccessToken(userId);
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
