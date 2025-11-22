package com.kiwoom.application.kiwoom.account.controller;

import com.kiwoom.application.common.ApiResponse;
import com.kiwoom.application.dto.StockInfoResponse;
import com.kiwoom.application.kiwoom.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

  private final AccountService accountService;

  @GetMapping("/myStock")
  public ResponseEntity<ApiResponse<StockInfoResponse>> getMyAccount(String date) {
    try {
      StockInfoResponse result = accountService.getMyAccount(date);
      return ApiResponse.success(result);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ApiResponse.error(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
