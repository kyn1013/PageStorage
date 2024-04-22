package com.example.PageStorage.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSaveRequestDto {

    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String userLoginId;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String userLoginPassword;

    @NotEmpty(message = "이름은 필수항목입니다.")
    private String name;

    private String nickName;
    private String phoneNumber;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String mail;
}
