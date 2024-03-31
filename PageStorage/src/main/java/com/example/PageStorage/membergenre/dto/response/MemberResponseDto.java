package com.example.PageStorage.membergenre.dto.response;

import com.example.PageStorage.entity.Member;
import com.example.PageStorage.member.dto.response.ResponseMemberInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {

    private String name;

    @Builder
    public MemberResponseDto (String name) {
        this.name = name;
    }

    public static List<MemberResponseDto> buildResponseDtoList (List<Member> members) {

        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for (Member member : members) {
            MemberResponseDto memberResponseDto =  MemberResponseDto.builder()
                    .name(member.getName())
                    .build();

            memberResponseDtos.add(memberResponseDto);
        }

        return memberResponseDtos;
    }
}
