package com.example.PageStorage.comment.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    private String content;
    private Long historySeq;
    private String userLoginId;

    @Builder
    public CommentRequestDto(String content, Long historySeq, String userLoginId){
        this.content = content;
        this.historySeq = historySeq;
        this.userLoginId = userLoginId;
    }

}
