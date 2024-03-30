package com.example.PageStorage.genre.dto.response;

import com.example.PageStorage.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GenreResponseDto {
    private String genreName;

    @Builder
    public GenreResponseDto(String genreName){
        this.genreName = genreName;
    }

    public static GenreResponseDto buildDto (Genre genre) {
        return GenreResponseDto.builder()
                .genreName(genre.getGenreName())
                .build();
    }

    public static List<GenreResponseDto> buildGenreResponseDtotoList (List<Genre> genres) {

        List<GenreResponseDto> genreResponseDtos = new ArrayList<>();

        for (Genre genre : genres) {
            GenreResponseDto genreResponseDto = GenreResponseDto.builder()
                    .genreName(genre.getGenreName())
                    .build();

            genreResponseDtos.add(genreResponseDto);
        }
        return genreResponseDtos;
    }

}
