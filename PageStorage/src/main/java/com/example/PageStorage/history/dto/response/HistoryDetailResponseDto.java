package com.example.PageStorage.history.dto.response;

import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.HistoryKeyword;
import com.example.PageStorage.entity.HistoryTag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String memberNickname;
    private Set<String> tagNames;
    private List<String> comments;
    private Set<String> keywords;
    private LocalDateTime createdDate;
    private String historyImage;
    private String profileImage;


    @Builder
    public HistoryDetailResponseDto(String historySeq, String bookName, String historyContent, String phrase,
                              String difficulty, String applicationToLife, String bookRecommender,
                              String memberNickname, Set<String> tagNames, List<String> comments, Set<String> keywords,
                                    LocalDateTime createdDate, String historyImage, String profileImage){
        this.historySeq = historySeq;
        this.bookName = bookName;
        this.historyContent = historyContent;
        this.phrase = phrase;
        this.difficulty = difficulty;
        this.applicationToLife = applicationToLife;
        this.bookRecommender = bookRecommender;
        this.memberNickname = memberNickname;
        this.tagNames = tagNames;
        this.comments = comments;
        this.keywords = keywords;
        this.createdDate = createdDate;
        this.historyImage = historyImage;
        this.profileImage = profileImage;
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

        Set<String> keywords = new HashSet<>(); // 태그 이름을 수집할 HashSet 초기화
        for (HistoryKeyword historyKeyword : history.getHistoryKeywords()) {
            keywords.add(historyKeyword.getKeyword().getKeyword()); // 태그 이름 추가
        }

        String profileFilename = null;

        // Member 객체와 MemberImage 객체가 null이 아닌지 확인
        if (history.getMember() != null && history.getMember().getMemberImage() != null) {
            profileFilename = history.getMember().getMemberImage().getStoreFilename();
        }

        return HistoryDetailResponseDto.builder()
                .historySeq(String.valueOf(history.getHistorySeq()))
                .bookName(history.getBookName())
                .historyContent(history.getHistoryContent())
                .phrase(history.getPhrase())
                .difficulty(history.getDifficulty())
                .applicationToLife(history.getApplicationToLife())
                .bookRecommender(history.getBookRecommender())
                .memberNickname(history.getMember().getNickName())
                .comments(comments)
                .tagNames(tagNames)
                .keywords(keywords)
                .createdDate(history.getCreatedDate())
                .historyImage(history.getHistoryImage().getStoreFilename())
                .profileImage(profileFilename)
                .build();
    }
}
