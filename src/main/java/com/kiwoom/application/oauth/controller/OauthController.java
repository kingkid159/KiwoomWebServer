package com.kiwoom.application.oauth.controller;

import com.kiwoom.application.common.ApiResponse;
import com.kiwoom.application.oauth.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OauthController {

  private final OauthService oauthService;

  @PostMapping("/getToken")
  public ResponseEntity<ApiResponse<String>> getToken() {
    try {
      oauthService.saveOauthToken();
      return ApiResponse.success("토큰 발급 성공");
    } catch (Exception e) {
      return ApiResponse.error("토큰 발급 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
