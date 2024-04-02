package com.example.PageStorage.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;
    private Long historySeq;
    private String memberName;

}
