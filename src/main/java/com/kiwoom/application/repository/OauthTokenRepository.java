package com.kiwoom.application.repository;

import com.kiwoom.application.entity.OauthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthTokenRepository extends JpaRepository<OauthToken, String> {

}
