package com.kiwoom.application.kiwoom.account.service;

import com.kiwoom.application.dto.MyAccountRequest;
import com.kiwoom.application.dto.StockInfoResponse;
import com.kiwoom.application.entity.OauthToken;
import com.kiwoom.application.entity.UserInfo;
import com.kiwoom.application.repository.OauthTokenRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

  private final OauthTokenRepository oauthTokenRepository;

  private final WebClient webClient;

  public StockInfoResponse getMyAccount(String date) throws Exception {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserInfo user = (UserInfo) auth.getPrincipal();

    String userId = user.getUserId();

    String url = "/api/dostk/acnt";
    MyAccountRequest accountRequest = new MyAccountRequest();

    OauthToken oauthToken = oauthTokenRepository.findById(userId).orElse(null);

    if (oauthToken == null) {
      throw new Exception("Oauth 토큰 없음");
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    LocalDateTime dateTime = LocalDateTime.parse(oauthToken.getExpiresDt(), formatter);

    if (dateTime.isBefore(LocalDateTime.now())) {
      throw new Exception("Oauth 토큰 만료됨");
    }

    accountRequest.setQryDt(date);

    Mono<ResponseEntity<StockInfoResponse>> response = webClient.post().uri(url)
        .headers(httpHeaders -> {
          httpHeaders.set("authorization", "Bearer " + oauthToken.getToken());
          httpHeaders.set("api-id", "ka01690");
        })
        .bodyValue(accountRequest)
        .retrieve()
        .toEntity(StockInfoResponse.class);

    ResponseEntity<StockInfoResponse> result = response.block();
    StockInfoResponse entity = result.getBody();

    return entity;
  }
}
