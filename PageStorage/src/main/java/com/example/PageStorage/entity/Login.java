package com.example.PageStorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login")
@Entity
public class Login {

    // Member 엔티티의 기본 키와 동일한 역할, 식별관계 설정
    @Id
    private Long id; // Member의 @Id 필드와 동일한 타입

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_seq")
    private Member member;

    @Column(name = "user_login_id", unique = true)
    private String userLoginId; //로그인 아이디

    @Column(name = "user_login_password")
    private String userLoginPassword; //로그인 비밀번호

    private String role;

    @Builder
    Login(String userLoginId, String userLoginPassword, String role) {
        this.userLoginId = userLoginId;
        this.userLoginPassword = userLoginPassword;
        this.role = role;
    }

    public void addMember(Member member) {
        this.member = member;
    }

}
