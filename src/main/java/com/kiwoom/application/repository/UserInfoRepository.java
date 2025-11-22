package com.kiwoom.application.repository;

import com.kiwoom.application.entity.UserInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

  UserInfo findByUserId(String userId);

  List<UserInfo> findByRefreshToken(String refreshToken);

  UserInfo findOneByRefreshToken(String refreshToken);
}
