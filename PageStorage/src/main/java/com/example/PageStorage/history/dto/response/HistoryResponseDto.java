package com.example.PageStorage.history.dto.response;

import com.example.PageStorage.comment.dto.response.CommentResponseDto;
import com.example.PageStorage.entity.Comment;
import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.HistoryImage;
import com.example.PageStorage.entity.HistoryTag;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    private String memberName;
    private String historyImage;
    private Set<String> tagNames;
    private List<String> comments;

    @Builder
    public HistoryResponseDto(String historySeq, String bookName, String historyContent, String phrase,
                             String difficulty, String applicationToLife, String bookRecommender,
                              String memberName, Set<String> tagNames, List<String> comments, String historyImage){
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
        this.historyImage = historyImage;

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



        return HistoryResponseDto.builder()
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
                .historyImage(history.getHistoryImage().getStoreFilename())
                .build();
    }

    public static List<HistoryResponseDto> buildDtoList(List<History> histories) { //오호?
        List<HistoryResponseDto> dtoList = new ArrayList<>();
        for (History history : histories) {
            dtoList.add(buildDto(history));
        }
        return dtoList;
    }

}
