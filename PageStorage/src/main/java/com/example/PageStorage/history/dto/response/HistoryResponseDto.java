package com.example.PageStorage.history.dto.response;

import com.example.PageStorage.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class HistoryResponseDto {

    private String historySeq;
    private String bookName;
    private String historyContent;
    private String phrase;
    private String difficulty;
    private String applicationToLife;
    private String bookRecommender;
    private String nickName;
    private String profileImage;
    private String historyImage;
    private Set<String> tagNames;
    private List<String> comments;
    private Set<String> keywords;

    @Builder
    public HistoryResponseDto(String historySeq, String bookName, String historyContent, String phrase,
                             String difficulty, String applicationToLife, String bookRecommender,
                              String nickName, Set<String> tagNames, List<String> comments, String historyImage, String profileImage, Set<String> keywords){
        this.historySeq = historySeq;
        this.bookName = bookName;
        this.historyContent = historyContent;
        this.phrase = phrase;
        this.difficulty = difficulty;
        this.applicationToLife = applicationToLife;
        this.bookRecommender = bookRecommender;
        this.nickName = nickName;
        this.tagNames = tagNames;
        this.comments = comments;
        this.historyImage = historyImage;
        this.profileImage = profileImage;
        this.keywords = keywords;
    }


    public static HistoryResponseDto buildDto(History history) {
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

        return HistoryResponseDto.builder()
                .historySeq(String.valueOf(history.getHistorySeq()))
                .bookName(history.getBookName())
                .historyContent(history.getHistoryContent())
                .phrase(history.getPhrase())
                .difficulty(history.getDifficulty())
                .applicationToLife(history.getApplicationToLife())
                .bookRecommender(history.getBookRecommender())
                .nickName(history.getMember().getNickName())
                .comments(comments)
                .tagNames(tagNames)
                .historyImage(history.getHistoryImage().getStoreFilename())
                .profileImage(profileFilename)
                .keywords(keywords)
                .build();
    }

    public static List<HistoryResponseDto> buildDtoList(List<History> histories) { //오호?
        List<HistoryResponseDto> dtoList = new ArrayList<>();
        for (History history : histories) {
            dtoList.add(buildDto(history));
        }
        return dtoList;
    }

    public static List<HistoryResponseDto> buildDtoAllList(List<History> histories) { //오호?
        List<HistoryResponseDto> dtoList = new ArrayList<>();
        for (History history : histories) {
            dtoList.add(buildDto(history));
        }
        return dtoList;
    }

}
