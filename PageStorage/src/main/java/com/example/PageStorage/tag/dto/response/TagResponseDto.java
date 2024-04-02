package com.example.PageStorage.tag.dto.response;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.Tag;
import com.example.PageStorage.genre.dto.response.GenreResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TagResponseDto {
    private String tagName;

    @Builder
    public TagResponseDto(String tagName){
        this.tagName = tagName;
    }

    public static TagResponseDto buildDto (Tag tag) {
        return TagResponseDto.builder()
                .tagName(tag.getTagName())
                .build();
    }

    public static List<TagResponseDto> buildDtotoList (List<Tag> tags) {

        List<TagResponseDto> tagResponseDtos = new ArrayList<>();

        for (Tag tag : tags) {
            tagResponseDtos.add(buildDto(tag));
        }
        return tagResponseDtos;
    }
}
