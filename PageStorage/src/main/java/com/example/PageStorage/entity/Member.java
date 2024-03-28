package com.example.PageStorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long memberSeq;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_nickName")
    private String nickName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "member_mail")
    private String mail;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Login login;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberGenre> memberGenres = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(String name, String nickName, String phoneNumber, String mail) {
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }
}
