package com.example.PageStorage.entity;

import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import com.example.PageStorage.member.dto.MemberUpdateRequestDto;
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

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "member_mail", unique = true)
    private String mail;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Login login;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberGenre> memberGenres = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MemberImage memberImage;

    @Builder
    public Member(String name, String nickName, String phoneNumber, String mail) {
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public void changeInfo (MemberUpdateRequestDto memberUpdateRequestDto) {
        this.nickName = memberUpdateRequestDto.getNickName();
//        this.phoneNumber = memberSaveRequestDto.getPhoneNumber();
//        this.mail = memberSaveRequestDto.getMail();
    }

    public void addMemberImage(MemberImage memberImage) {
        this.memberImage = memberImage;
    }

    public void changeProfile (MemberUpdateRequestDto memberUpdateRequestDto, MemberImage memberImage) {
        this.nickName = memberUpdateRequestDto.getNickName();
        this.memberImage = memberImage;
    }


}
