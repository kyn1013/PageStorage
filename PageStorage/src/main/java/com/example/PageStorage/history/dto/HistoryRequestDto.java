package com.example.PageStorage.history.dto;

import com.example.PageStorage.tag.dto.TagRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class HistoryRequestDto {

    private String bookName;
    private String historyContent;
    private String phrase;
    private String difficulty;
    private String applicationToLife;
    private String bookRecommender;
    private String memberName;
    private Set<TagRequestDto> tagRequestDtos;

}
