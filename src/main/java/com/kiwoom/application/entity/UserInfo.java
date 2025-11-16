package com.kiwoom.application.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;
    private String password;
    private String nickName;
}
