package com.kiwoom.application.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    //TODO 테스트용 시크릿 키 배포시엔 환경변수로 시크릿 키를 설정해야함
    private static final String SECRET_KEY = "EUyg1IG+I4cubymmLzIDztyJUcMpaIquQq0sTY5B3kc=";
    private SecretKey key;

    // Access Token: 10분
    private final long ACCESS_TOKEN_EXPIRE = 1000 * 60 * 10;

    // Refresh Token: 14일
    private final long REFRESH_TOKEN_EXPIRE = 1000L * 60 * 60 * 24 * 14;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // --------------------
    // Token 생성
    // --------------------
    public String createAccessToken(String userId) {
        return createToken(userId, ACCESS_TOKEN_EXPIRE, "access");
    }

    public String createRefreshToken(String userId) {
        return createToken(userId, REFRESH_TOKEN_EXPIRE, "refresh");
    }

    private String createToken(String userId, long expireTime, String tokenType) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("type", tokenType)      // token type: access / refresh
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    // --------------------
    // Token 검증
    // --------------------
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // --------------------
    // Claims 파싱
    // --------------------
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // --------------------
    // Payload 정보 추출
    // --------------------
    public Long getUserId(String token) {
        Claims claims = parseClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    public String getTokenType(String token) {
        Claims claims = parseClaims(token);
        return claims.get("type", String.class);
    }
}
