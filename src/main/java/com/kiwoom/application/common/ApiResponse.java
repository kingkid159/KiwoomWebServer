package com.kiwoom.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {

  private T data;
  private int status;
  private String message;
  private Boolean success;

  public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
    ApiResponse<T> body = new ApiResponse<>(data, HttpStatus.OK.value(), "success", true);
    return ResponseEntity.ok(body);
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(T data, HttpStatus status) {
    ApiResponse<T> body = new ApiResponse<>(data, HttpStatus.OK.value(), "success", true);
    return ResponseEntity.status(status).body(body);
  }
}
