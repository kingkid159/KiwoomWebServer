package com.kiwoom.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OauthTokenRequest {

  private String grant_type;
  private String appkey;
  private String secretkey;
}
