package com.kiwoom.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "oauth_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OauthToken {

  @Id
  private String userId;
  private String token;
  private String tokenType;
  private String expiresDt;

}
