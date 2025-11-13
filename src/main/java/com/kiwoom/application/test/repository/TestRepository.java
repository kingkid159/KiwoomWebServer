package com.kiwoom.application.test.repository;

import com.kiwoom.application.test.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository< TestEntity,String> {

}
