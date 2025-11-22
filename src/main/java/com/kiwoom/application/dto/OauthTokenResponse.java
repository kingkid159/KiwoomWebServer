package com.kiwoom.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OauthTokenResponse {

  private String expires_dt;
  private String token_type;
  private String token;
  private String return_code;
  private String return_msg;

}
