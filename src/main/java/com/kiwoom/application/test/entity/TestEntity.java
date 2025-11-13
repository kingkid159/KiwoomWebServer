package com.kiwoom.application.test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="test")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestEntity {
    @Id
    private String test;
}
