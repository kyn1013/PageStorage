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
import com.example.PageStorage.member.dto.response.ResponseMemberInfoDto;
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
    private final HistoryService historyService;

    /*
   댓글 추가
    */
//    @PostMapping()
//    public ResponseEntity<ResBodyModel> commentSave(@RequestBody CommentRequestDto commentRequestDto) {
//
//        Comment comment = commentService.saveComment(commentRequestDto);
//        CommentResponseDto commentResponseDto = CommentResponseDto.buildDto(comment);
//        return PsResponse.toResponse(SuccessCode.SUCCES,commentResponseDto);
//    }

    @PostMapping("/create/{id}")
    public String commentSave(@PathVariable Long id, @ModelAttribute("commentForm") CommentRequestDto commentRequestDto,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentRequestDto.setUserLoginId(userDetails.getUsername());
        commentService.saveComment(id, commentRequestDto);
        return String.format("redirect:/histories/read/%s", id);
    }

    /*
    댓글 조회
     */
    @GetMapping("/read/{commentSeq}")
    public ResponseEntity<ResBodyModel> readCommentBySeq(@PathVariable Long commentSeq) {

        Comment comment = commentService.find(commentSeq);
        CommentResponseDto commentResponseDto = CommentResponseDto.buildDto(comment);
        return PsResponse.toResponse(SuccessCode.SUCCES,commentResponseDto);
    }

    @GetMapping("/readMember/{memberName}")
    public ResponseEntity<ResBodyModel> readCommentByMemberName(@PathVariable String memberName) {

        List<Comment> comments = commentService.findByMember(memberName);
        List<CommentResponseDto> commentResponseDtos = CommentResponseDto.buildDtoList(comments);
        return PsResponse.toResponse(SuccessCode.SUCCES,commentResponseDtos);
    }

    @GetMapping("/{historySeq}")
    public ResponseEntity<ResBodyModel> readCommentByHistorySeq(@PathVariable Long historySeq) {

        List<Comment> comments = commentService.findByHistory(historySeq);
        List<CommentResponseDto> commentResponseDtos = CommentResponseDto.buildDtoList(comments);
        return PsResponse.toResponse(SuccessCode.SUCCES,commentResponseDtos);
    }

    @GetMapping("/all")
    public ResponseEntity<ResBodyModel> readAll() {

        List<Comment> comments = commentService.findAll();
        List<CommentResponseDto> commentResponseDtos = CommentResponseDto.buildDtoList(comments);
        return PsResponse.toResponse(SuccessCode.SUCCES,commentResponseDtos);
    }

    /*
    수정
     */
    @PatchMapping()
    public ResponseEntity<ResBodyModel> updateComment(@RequestBody CommentRequestDto commentRequestDto){
        Comment comment = commentService.update(commentRequestDto);
        CommentResponseDto commentResponseDto = CommentResponseDto.buildDto(comment);
        return PsResponse.toResponse(SuccessCode.SUCCES,commentResponseDto);
    }

    /*
    삭제
     */
    @DeleteMapping("/delete/{commentSeq}")
    public ResponseEntity<ResBodyModel> delete(@PathVariable Long commentSeq) {
        commentService.delete(commentSeq);
        return PsResponse.toResponse(SuccessCode.SUCCES);
    }

}
