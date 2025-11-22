package com.kiwoom.application.jwt;

import com.kiwoom.application.entity.UserInfo;
import com.kiwoom.application.repository.UserInfoRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtProvider;
  private final UserInfoRepository userInfoRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      String token = resolveToken(request);

      // 3. JWT 검증
      if (jwtProvider.validateToken(token)) {
        String userId = jwtProvider.getUserId(token);
        UserInfo userDetails = userInfoRepository.findByUserId(userId);

        // 4. Authentication 생성 (ROLE_USER 기본값)
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 5. SecurityContextHolder에 세팅
        SecurityContextHolder.getContext().setAuthentication(auth);
      }

    } catch (Exception ex) {
      // 여기서 토큰 검증 실패시 처리 (로그, 응답 등)
    }

    filterChain.doFilter(request, response);
  }

  /**
   * Authorization 헤더에서 Bearer 토큰 추출
   */
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
