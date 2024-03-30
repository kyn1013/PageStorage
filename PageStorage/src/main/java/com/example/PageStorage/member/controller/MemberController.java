package com.example.PageStorage.member.controller;

import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import com.example.PageStorage.member.dto.response.ResponseMemberInfoDto;
import com.example.PageStorage.member.service.MemberService;
import com.example.PageStorage.common.model.ResBodyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PageStorage.code.SuccessCode;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /*
    회원 가입
     */
    @PostMapping("/join")
    public ResponseEntity<ResBodyModel> joinMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {

        Member member = memberService.saveMember(memberSaveRequestDto);
        ResponseMemberInfoDto memberResponseInfoDto = ResponseMemberInfoDto.buildDto(member);
        return PsResponse.toResponse(SuccessCode.SUCCES, memberResponseInfoDto);
    }

    /*
    회원 조회
     */
    @GetMapping("/user/{memberId}") //단일 회원 조회
    public ResponseEntity<ResBodyModel> readMemberId(@PathVariable String memberId) {
        Member member = memberService.find(memberId);
        ResponseMemberInfoDto memberResponseInfoDto = ResponseMemberInfoDto.buildDto(member);
        return PsResponse.toResponse(SuccessCode.SUCCES, memberResponseInfoDto);
    }

    @GetMapping("/{nickName}") //단일 회원 조회
    public ResponseEntity<ResBodyModel> readNickName(@PathVariable String nickName) {
        Member member = memberService.findNickName(nickName);
        ResponseMemberInfoDto memberResponseInfoDto = ResponseMemberInfoDto.buildDto(member);
        return PsResponse.toResponse(SuccessCode.SUCCES, memberResponseInfoDto);
    }

    @GetMapping("/all") //회원 전체 조회
    public ResponseEntity<ResBodyModel> readAll() {
        List<Member> members = memberService.findAll();
        List<ResponseMemberInfoDto> memberResponseInfoDtoList = ResponseMemberInfoDto.buildResponseMemberDtoList(members);
        return PsResponse.toResponse(SuccessCode.SUCCES, memberResponseInfoDtoList);
    }

    /*
    회원 수정
     */
    @PatchMapping()
    public ResponseEntity<ResBodyModel> updateMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto){
        Member member = memberService.update(memberSaveRequestDto);
        ResponseMemberInfoDto memberResponseInfoDto = ResponseMemberInfoDto.buildDto(member);
        return PsResponse.toResponse(SuccessCode.SUCCES, memberResponseInfoDto);
    }

    /*
    회원 삭제
     */
    @DeleteMapping("delete/{memberSeq}") //회원 삭제
    public ResponseEntity<ResBodyModel> delete(@PathVariable Long memberSeq) {
        memberService.delete(memberSeq);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

//    @DeleteMapping("/{userLoginId}") //회원 삭제
//    public ResponseEntity<ResBodyModel> delete(@PathVariable String userLoginId) {
//        Login login = memberService.delete(userLoginId);
//        return PsResponse.toResponse(SuccessCode.SUCCES, login);
//    }


}
