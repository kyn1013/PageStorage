package com.example.PageStorage.member.controller;

import com.example.PageStorage.common.exception.member.BothLoginIdAndMailExistsException;
import com.example.PageStorage.common.exception.member.LoginIdAlreadyExistsException;
import com.example.PageStorage.common.exception.member.MailAlreadyExistsException;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import com.example.PageStorage.member.dto.MemberUpdateRequestDto;
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
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /*
    회원 가입
     */
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
        try {
            memberService.saveMember(memberSaveRequestDto);
        } catch (BothLoginIdAndMailExistsException e) {
            bindingResult.rejectValue("userLoginId", "error.userLoginId", e.getMessage());
            bindingResult.rejectValue("mail", "error.mail", e.getMessage());
        } catch (LoginIdAlreadyExistsException e) {
            bindingResult.rejectValue("userLoginId", "error.userLoginId", e.getMessage());
        } catch (MailAlreadyExistsException e) {
            bindingResult.rejectValue("mail", "error.mail", e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            return "members/createJoinForm";
        }
        return "redirect:/members/login";
    }

    /*
    회원프로필 수정
     */
    @GetMapping("/update/username")
    public String updateUsernameForm(@ModelAttribute("memberUpdateForm") MemberUpdateRequestDto memberUpdateRequestDto
    , @AuthenticationPrincipal CustomUserDetails userDetails) {

        Member member = memberService.find(userDetails.getUserLoginId());
        memberUpdateRequestDto.setNickName(member.getNickName());

        if(member.getMemberImage() != null && member.getMemberImage().getOriginFilename() != null) {
            memberUpdateRequestDto.setFileName(member.getMemberImage().getOriginFilename());
        } else {
            memberUpdateRequestDto.setFileName(null);
        }

        return "members/updateForm";
    }

    @PostMapping("/update/user")
    public String createHistory(@ModelAttribute("memberUpdateForm") MemberUpdateRequestDto memberUpdateRequestDto,
                                @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {

        memberUpdateRequestDto.setUserLoginId(userDetails.getUserLoginId());
        memberService.updateProfile(memberUpdateRequestDto);
        return "redirect:/histories/all";
    }

}
