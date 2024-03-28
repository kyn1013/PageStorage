package com.example.PageStorage.member.dto.response;

import com.example.PageStorage.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMemberInfoDto {

    private String name;
    private String nickName;
    private String phoneNumber;
    private String mail;

    @Builder
    public ResponseMemberInfoDto(String name,  String nickName, String phoneNumber, String mail) {
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    //entity에서 Dto로
    public static ResponseMemberInfoDto buildDto (Member member) {
        return ResponseMemberInfoDto.builder()
                .name(member.getName())
                .nickName(member.getNickName())
                .phoneNumber(member.getPhoneNumber())
                .mail(member.getMail())
                .build();
    }

    public static List<ResponseMemberInfoDto> buildMemberDtoList (List<Member> members) {
        return members.stream().map(member -> {
            return ResponseMemberInfoDto.builder()
                    .name(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .mail(member.getMail())
                    .build();
        }).collect(Collectors.toList());
    }
}