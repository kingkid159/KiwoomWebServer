package com.kiwoom.application.login.controller;

import com.kiwoom.application.common.ResponseEntity;
import com.kiwoom.application.config.JwtUtil;
import com.kiwoom.application.dto.LoginRequest;
import com.kiwoom.application.login.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse res) {

        try{
            loginService.login(request);
        }catch(Exception e){
            return ResponseEntity.error("로그인 실패");
        }

        String accessToken = jwtUtil.createAccessToken(request.getLoginId());
        String refreshToken = jwtUtil.createRefreshToken(request.getLoginId());

        // Refresh Token을 HttpOnly 쿠키로 내려줌
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 14); // 14일
        res.addCookie(cookie);
        return ResponseEntity.success(accessToken);
    }
}
