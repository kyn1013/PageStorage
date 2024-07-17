package com.example.PageStorage.comment.controller;

import com.example.PageStorage.comment.dto.CommentRequestDto;
import com.example.PageStorage.comment.dto.response.CommentResponseDto;
import com.example.PageStorage.comment.service.CommentService;
import com.example.PageStorage.common.PsResponse;
import com.example.PageStorage.common.code.SuccessCode;
import com.example.PageStorage.common.model.ResBodyModel;
import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.history.service.HistoryService;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import com.example.PageStorage.security.login.dto.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    /*
   댓글 추가
    */
    @PostMapping("/create/{id}")
    public String commentSave(@PathVariable Long id, @ModelAttribute("commentForm") CommentRequestDto commentRequestDto,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentRequestDto.setUserLoginId(userDetails.getUsername());
        commentService.saveComment(id, commentRequestDto);
        return String.format("redirect:/histories/read/%s", id);
    }

    /*
    삭제
     */
    @GetMapping("/delete/{commentSeq}")
    public String delete(@PathVariable Long commentSeq) {
        commentService.delete(commentSeq);
        return "redirect:/histories/all";
    }


}
