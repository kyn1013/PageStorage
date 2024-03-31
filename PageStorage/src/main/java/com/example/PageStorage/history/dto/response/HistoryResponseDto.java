package com.example.PageStorage.history.dto.response;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HistoryResponseDto {

    private String bookName;
    private String historyContent;
    private String phrase;
    private String difficulty;
    private String applicationToLife;
    private String bookRecommender;
    private String memberName;

    @Builder
    public HistoryResponseDto(String bookName, String historyContent, String phrase,
                             String difficulty, String applicationToLife, String bookRecommender, String memberName){
        this.bookName = bookName;
        this.historyContent = historyContent;
        this.phrase = phrase;
        this.difficulty = difficulty;
        this.applicationToLife = applicationToLife;
        this.bookRecommender = bookRecommender;
        this.memberName = memberName;
    }

    public static HistoryResponseDto buildDto(History history) {
        return HistoryResponseDto.builder()
                .bookName(history.getBookName())
                .historyContent(history.getHistoryContent())
                .phrase(history.getPhrase())
                .difficulty(history.getDifficulty())
                .applicationToLife(history.getApplicationToLife())
                .bookRecommender(history.getBookRecommender())
                .memberName(history.getMember().getName())
                .build();
    }

    public static List<HistoryResponseDto> buildDtoList(List<History> histories) { //오호라?
        List<HistoryResponseDto> dtoList = new ArrayList<>();
        for (History history : histories) {
            dtoList.add(buildDto(history));
        }
        return dtoList;
    }

}
