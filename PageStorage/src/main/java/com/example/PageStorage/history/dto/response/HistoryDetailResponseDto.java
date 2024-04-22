package com.example.PageStorage.history.dto.response;

import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.HistoryTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class HistoryDetailResponseDto {

    private String historySeq;
    private String bookName;
    private String historyContent;
    private String phrase;
    private String difficulty;
    private String applicationToLife;
    private String bookRecommender;
    private String memberName;
    private Set<String> tagNames;
    private List<String> comments;

    @Builder
    public HistoryDetailResponseDto(String historySeq, String bookName, String historyContent, String phrase,
                              String difficulty, String applicationToLife, String bookRecommender,
                              String memberName, Set<String> tagNames, List<String> comments){
        this.historySeq = historySeq;
        this.bookName = bookName;
        this.historyContent = historyContent;
        this.phrase = phrase;
        this.difficulty = difficulty;
        this.applicationToLife = applicationToLife;
        this.bookRecommender = bookRecommender;
        this.memberName = memberName;
        this.tagNames = tagNames;
        this.comments = comments;
    }

    public static HistoryDetailResponseDto buildDto(History history) {
        Set<String> tagNames = new HashSet<>(); // 태그 이름을 수집할 HashSet 초기화
        for (HistoryTag historyTag : history.getHistoryTags()) {
            tagNames.add(historyTag.getTag().getTagName()); // 태그 이름 추가
        }

        List<String> comments = new ArrayList<>();
        for (Comment comment : history.getComments()) {
            comments.add(comment.getContent());
        }

        return HistoryDetailResponseDto.builder()
                .historySeq(String.valueOf(history.getHistorySeq()))
                .bookName(history.getBookName())
                .historyContent(history.getHistoryContent())
                .phrase(history.getPhrase())
                .difficulty(history.getDifficulty())
                .applicationToLife(history.getApplicationToLife())
                .bookRecommender(history.getBookRecommender())
                .memberName(history.getMember().getName())
                .comments(comments)
                .tagNames(tagNames)
                .build();
    }
}
