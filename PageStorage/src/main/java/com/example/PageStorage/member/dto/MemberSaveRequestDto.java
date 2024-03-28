package com.example.PageStorage.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSaveRequestDto {

    private String userLoginId;
    private String userLoginPassword;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String mail;
}
