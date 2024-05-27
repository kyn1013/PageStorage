package com.example.PageStorage.comment.dto.response;

import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.history.dto.response.HistoryResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private String content;
    private Long historySeq;
    private String memberName;
    private Long commentSeq;
    private String profileImage;

    @Builder
    public CommentResponseDto(String content, Long historySeq, String memberName, Long commentSeq, String profileImage){
        this.content = content;
        this.historySeq = historySeq;
        this.memberName = memberName;
        this.commentSeq = commentSeq;
        this.profileImage = profileImage;
    }

    public static CommentResponseDto buildDto(Comment comment){
        String profileFilename = null;

        // Member 객체와 MemberImage 객체가 null이 아닌지 확인
        if (comment.getMember() != null && comment.getMember().getMemberImage() != null) {
            profileFilename = comment.getMember().getMemberImage().getStoreFilename();
        }


        return CommentResponseDto.builder()
                .content(comment.getContent())
                .historySeq(comment.getHistory().getHistorySeq())
                .memberName(comment.getMember().getName())
                .commentSeq(comment.getCommentSeq())
                .profileImage(profileFilename)
                .build();
    }

    public static List<CommentResponseDto> buildDtoList(List<Comment> comments) { //오호?
        List<CommentResponseDto> dtoList = new ArrayList<>();
        for (Comment comment : comments) {
            dtoList.add(buildDto(comment));
        }
        return dtoList;
    }
}
