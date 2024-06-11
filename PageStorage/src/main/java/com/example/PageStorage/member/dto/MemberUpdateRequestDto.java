package com.example.PageStorage.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberUpdateRequestDto {

    private String userLoginId;
    private String nickName;
    private String fileName;
    private MultipartFile imageFile;
}
