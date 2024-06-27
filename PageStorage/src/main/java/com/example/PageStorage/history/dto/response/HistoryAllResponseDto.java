package com.example.PageStorage.history.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.Set;

public class HistoryAllResponseDto {

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
    public HistoryAllResponseDto(String historySeq, String bookName, String historyContent, String phrase,
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
}
