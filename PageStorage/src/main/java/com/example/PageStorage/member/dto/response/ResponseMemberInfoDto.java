package com.example.PageStorage.member.dto.response;

import com.example.PageStorage.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ResponseMemberInfoDto> buildResponseMemberDtoList (List<Member> members) {
//        return members.stream().map(member -> {
//            return ResponseMemberInfoDto.builder()
//                    .name(member.getName())
//                    .phoneNumber(member.getPhoneNumber())
//                    .mail(member.getMail())
//                    .build();
//        }).collect(Collectors.toList());

        List<ResponseMemberInfoDto> responseMemberInfoDtos = new ArrayList<>();
        for (Member member : members) {
            ResponseMemberInfoDto responseMemberInfoDto = ResponseMemberInfoDto.builder()
                    .name(member.getName())
                    .nickName(member.getNickName())
                    .phoneNumber(member.getPhoneNumber())
                    .mail(member.getMail())
                    .build();

            responseMemberInfoDtos.add(responseMemberInfoDto);
        }
        return responseMemberInfoDtos;
    }
}
