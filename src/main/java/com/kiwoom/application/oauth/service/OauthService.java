package com.kiwoom.application.oauth.service;

import com.kiwoom.application.dto.OauthTokenRequest;
import com.kiwoom.application.dto.OauthTokenResponse;
import com.kiwoom.application.entity.OauthToken;
import com.kiwoom.application.entity.UserInfo;
import com.kiwoom.application.repository.OauthTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class OauthService {

  private final WebClient webClient;

  private final OauthTokenRepository oauthTokenRepository;

  public void saveOauthToken() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserInfo user = (UserInfo) auth.getPrincipal();

    String userId = user.getUserId();

    String url = "/oauth2/token";
    OauthTokenRequest request = new OauthTokenRequest();
    request.setGrant_type("client_credentials");
    request.setAppkey("-IsdjvJR7OiYQY27mHMc9eigItOZaOp053ekbYpwS0k");
    request.setSecretkey("bGvaHkF-AMjSxW3r3uwhp6MHcWIGLuERlBufSw43_XQ");

    Mono<ResponseEntity<OauthTokenResponse>> response = webClient.post().uri(url).bodyValue(request)
        .retrieve()
        .toEntity(OauthTokenResponse.class);

    ResponseEntity<OauthTokenResponse> result = response.block();

    if (result == null) {
      throw new RuntimeException("Oauth token could not be retrieved");
    }
    OauthTokenResponse oauthTokenResponse = result.getBody();
    if (oauthTokenResponse == null) {
      throw new RuntimeException("Oauth token could not be retrieved");
    }
    if (!"0".equals(oauthTokenResponse.getReturn_code())) {
      throw new RuntimeException("Oauth token could not be retrieved");
    }
    oauthTokenRepository.save(
        OauthToken.builder().userId(userId).token(oauthTokenResponse.getToken())
            .tokenType(oauthTokenResponse.getToken_type())
            .expiresDt(oauthTokenResponse.getExpires_dt()).build());
  }
}
