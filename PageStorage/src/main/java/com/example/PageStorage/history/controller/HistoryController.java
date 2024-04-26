package com.example.PageStorage.history.controller;

import com.example.PageStorage.comment.dto.CommentRequestDto;
import com.example.PageStorage.comment.dto.response.CommentResponseDto;
import com.example.PageStorage.comment.service.CommentService;
import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.code.SuccessCode;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.history.dto.HistoryDeleteDto;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.history.dto.response.HistoryDetailResponseDto;
import com.example.PageStorage.history.dto.response.HistoryResponseDto;
import com.example.PageStorage.history.service.HistoryService;
import com.example.PageStorage.member.service.MemberService;
import com.example.PageStorage.security.login.dto.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/histories")
public class HistoryController {

    private final MemberService memberService;
    private final HistoryService historyService;
    private final CommentService commentService;

    /*
    히스토리 추가
     */
    @GetMapping("/new")
    public String createHistoryForm(Model model) {

        model.addAttribute("historyForm", new HistoryRequestDto());
        return "createHistoryForm";
    }

    @PostMapping("/new")
    public String createHistory(@ModelAttribute("historyForm") @Valid HistoryRequestDto historyRequestDto,
                                BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        if (bindingResult.hasErrors()) {
            return "createHistoryForm";
        }

        historyRequestDto.setUserLoginId( userDetails.getUsername());
        historyService.saveHistory(historyRequestDto);
        return "redirect:/histories/all";
    }

    /*
    히스토리 조회
     */
//    @GetMapping("/read/{historySeq}")
//    public ResponseEntity<ResBodyModel> findBySeq(@PathVariable Long historySeq) {
//        History history = historyService.find(historySeq);
//        HistoryResponseDto historyResponseDto = HistoryResponseDto.buildDto(history);
//        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDto);
//    }

    @GetMapping("/myPage")
    public String myPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        String id = userDetails.getUsername();
        String mail = userDetails.getMail();
        String userNickName = userDetails.getNickname();

        List<History> histories = historyService.findByMail(mail);
        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
        model.addAttribute("history", historyResponseDtos);
        model.addAttribute("nickName", userNickName);

        Member member = memberService.find(id);
        model.addAttribute("member", member);

        return "my_history_view"; // Thymeleaf 템플릿 파일 이름 반환
    }

    @GetMapping("/bookName/{bookName}")
    public ResponseEntity<ResBodyModel> findByBookName(@PathVariable String bookName) {
        List<History> histories = historyService.findHistoriesByBookName(bookName);
        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDtos);
    }

//    @GetMapping("/all")
//    public ResponseEntity<ResBodyModel> findAll () {
//        List<History> histories = historyService.findAll();
//        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);
//        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDtos);
//    }

    @GetMapping("/all")
    public String findAll (@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<History> histories = historyService.findAll();
        List<HistoryResponseDto> historyResponseDtos = HistoryResponseDto.buildDtoList(histories);

        model.addAttribute("history", historyResponseDtos);

        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        String nickname = userDetails.getNickname();
        model.addAttribute("nickName", nickname);

        Member member = memberService.find(id);
        model.addAttribute("member", member);

        return "history_view"; // Thymeleaf 템플릿 파일 이름 반환
    }

    @GetMapping(value = "/read/{historySeq}")
    public String findBySeq(Model model, @PathVariable Long historySeq, CommentRequestDto commentRequestDto) {
        History history = historyService.find(historySeq);
        List<Comment> comments = commentService.findByHistory(historySeq);
        List<CommentResponseDto> commentResponseDtos = CommentResponseDto.buildDtoList(comments);

        HistoryDetailResponseDto historyDetailResponseDto = HistoryDetailResponseDto.buildDto(history);
        model.addAttribute("history", historyDetailResponseDto);
        model.addAttribute("realComment", commentResponseDtos);
        model.addAttribute("commentForm", commentRequestDto);
        return "history_detail_view";
    }

    /*
    히스토리 수정
     */
    @PatchMapping("/update")
    public ResponseEntity<ResBodyModel> updateHistory(@RequestBody HistoryRequestDto historyRequestDto){
        History history = historyService.update(historyRequestDto);
        HistoryResponseDto historyResponseDto = HistoryResponseDto.buildDto(history);
        return PsResponse.toResponse(SuccessCode.SUCCES,historyResponseDto);
    }

    /*
    히스토리 삭제
     */
    @DeleteMapping("/{historySeq}")
    public ResponseEntity<ResBodyModel> deleteBySeq(@PathVariable Long historySeq) {
        historyService.delete(historySeq);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

    @DeleteMapping()
    public ResponseEntity<ResBodyModel> delete(@RequestBody HistoryDeleteDto historyDeleteDto) {
        historyService.delete(historyDeleteDto);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

}
