package com.example.PageStorage.genre.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenreUpdateRequestDto {
    private String genreName;
    private Long genreSeq;
}
