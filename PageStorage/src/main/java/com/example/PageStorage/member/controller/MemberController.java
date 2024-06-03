package com.example.PageStorage.member.controller;

import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import com.example.PageStorage.member.dto.MemberUpdateRequestDto;
import com.example.PageStorage.member.dto.response.MemberResponseDTO;
import com.example.PageStorage.member.dto.response.ResponseMemberInfoDto;
import com.example.PageStorage.member.service.MemberService;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.security.login.dto.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.PageStorage.common.code.SuccessCode;

import java.io.IOException;
import java.util.List;

//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /*
    회원 가입
     */
//    @PostMapping("/join")
//    public ResponseEntity<ResBodyModel> joinMember(@RequestBody MemberSaveRequestDto memberSaveRequestDto) {
//
//        Member member = memberService.saveMember(memberSaveRequestDto);
//        ResponseMemberInfoDto memberResponseInfoDto = ResponseMemberInfoDto.buildDto(member);
//        return PsResponse.toResponse(SuccessCode.SUCCES, memberResponseInfoDto);
//    }

    @GetMapping("/join")
    public String createJoinForm(Model model) {
        model.addAttribute("joinForm", new MemberSaveRequestDto());
        return "members/createJoinForm";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("joinForm") @Valid MemberSaveRequestDto memberSaveRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/createJoinForm";
        }
        memberService.saveMember(memberSaveRequestDto);
        return "redirect:/members/login";
    }

    /*
    회원프로필 수정
     */
    @GetMapping("/update/username")
    public String updateUsernameForm(Model model) {
        model.addAttribute("memberUpdateForm", new MemberUpdateRequestDto());
        System.out.println("sssssss");
        return "members/updateForm";
    }

    @PostMapping("/update/username")
    public String createHistory(@ModelAttribute("memberUpdateForm") MemberUpdateRequestDto memberUpdateRequestDto,
                                @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {

        memberUpdateRequestDto.setUserLoginId(userDetails.getMail());
        memberService.updateProfile(memberUpdateRequestDto);
        System.out.println("afdasfad");
        return "redirect:/histories/all";
    }



    /*
    로그인
     */



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
    @PatchMapping("/")
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
