package com.example.PageStorage.history.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class HistoryAllResponseDto {

    private List<HistoryResponseDto> historyResponseDtos;
    private Long cursorId;
    private boolean hasNext;

    public HistoryAllResponseDto(List<HistoryResponseDto> historyResponseDtos, Long cursorId, boolean hasNext) {
        this.historyResponseDtos = historyResponseDtos;
        this.cursorId = cursorId;
        this.hasNext = hasNext;
    }

}
