package com.kiwoom.application.repository;

import com.kiwoom.application.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    List<UserInfo> findByUserId(String userId);
}
