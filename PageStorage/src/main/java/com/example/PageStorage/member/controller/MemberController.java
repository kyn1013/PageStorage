package com.example.PageStorage.member.controller;

import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import com.example.PageStorage.member.dto.response.ResponseMemberInfoDto;
import com.example.PageStorage.member.service.MemberService;
import com.example.PageStorage.common.model.ResBodyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.PageStorage.code.SuccessCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<ResBodyModel> joinMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {

        Member member = memberService.saveMember(memberSaveRequestDto);
        ResponseMemberInfoDto memberResponseInfoDto = ResponseMemberInfoDto.buildDto(member);
        return PsResponse.toResponse(SuccessCode.SUCCES, memberResponseInfoDto);
    }


}
