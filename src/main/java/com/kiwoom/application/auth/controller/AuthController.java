package com.kiwoom.application.auth.controller;

//import com.kiwoom.application.common.CommonResponseEntity;

import com.kiwoom.application.auth.service.AuthService;
import com.kiwoom.application.common.ApiResponse;
import com.kiwoom.application.dto.LoginRequest;
import com.kiwoom.application.entity.UserInfo;
import com.kiwoom.application.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService loginService;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request,
      HttpServletResponse res) {

    try {
      String accessToken = jwtUtil.createAccessToken(request.getLoginId());
      String refreshToken = jwtUtil.createRefreshToken(request.getLoginId());

      UserInfo userInfo = loginService.login(request);
      userInfo.setRefreshToken(refreshToken);
      userInfo.setExpireTime(LocalDateTime.now().plusDays(14));

      Cookie cookie = new Cookie("refreshToken", refreshToken);
      cookie.setHttpOnly(true);
      cookie.setSecure(true);
      cookie.setPath("/");
      cookie.setMaxAge(60 * 60 * 24 * 14); // 14일
      res.addCookie(cookie);
      return ApiResponse.success(accessToken);
    } catch (Exception e) {
      return ApiResponse.error("로그인 실패", HttpStatus.UNAUTHORIZED);
    }
  }

  @GetMapping("/accessToken")
  public ResponseEntity<ApiResponse<String>> accessToken(HttpServletRequest req,
      HttpServletResponse res) {

    String refreshToken = Arrays.stream(Optional.ofNullable(req.getCookies()).orElse(new Cookie[0]))
        .filter(c -> c.getName().equals("refreshToken"))
        .findFirst()
        .map(Cookie::getValue)
        .orElse(null);

    try {
      String newAccessToken = loginService.refresh(refreshToken);
      return ApiResponse.success(newAccessToken);
    } catch (Exception e) {
      return ApiResponse.error("refreshToken 확인 실패", HttpStatus.UNAUTHORIZED);
    }
  }
//
//  @PostMapping("/refreshToken")
//  public ResponseEntity<String> refreshToken(HttpServletRequest req, HttpServletResponse res) {
//
//    String refreshToken = Arrays.stream(Optional.ofNullable(req.getCookies()).orElse(new Cookie[0]))
//        .filter(c -> c.getName().equals("refreshToken"))
//        .findFirst()
//        .map(Cookie::getValue)
//        .orElse(null);
//
//    try {
//      String newAccessToken = loginService.refresh(refreshToken);
//      return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
//    } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refreshToken 확인 실패");
//    }
//  }

}
