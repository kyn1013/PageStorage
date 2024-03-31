package com.example.PageStorage.membergenre.dto.response;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.MemberGenre;
import com.example.PageStorage.genre.dto.response.GenreResponseDto;
import com.example.PageStorage.membergenre.dto.MemberGenreRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberGenreResponseDto {
    private String memberName;
    private String genreName;

    @Builder
    public MemberGenreResponseDto (String memberName, String genreName){
        this.memberName = memberName;
        this.genreName = genreName;
    }

    public static MemberGenreResponseDto buildDto (MemberGenre memberGenre) {
        return MemberGenreResponseDto.builder()
                .memberName(memberGenre.getMember().getName())
                .genreName(memberGenre.getGenre().getGenreName())
                .build();
    }

    public static List<MemberGenreResponseDto> buildDtoToList (List<MemberGenre> memberGenres) {
        List<MemberGenreResponseDto> memberGenreResponseDtos = new ArrayList<>();

        for (MemberGenre memberGenre : memberGenres) {
            MemberGenreResponseDto memberGenreResponseDto = MemberGenreResponseDto.builder()
                    .memberName(memberGenre.getMember().getName())
                    .genreName(memberGenre.getGenre().getGenreName())
                    .build();

            memberGenreResponseDtos.add(memberGenreResponseDto);
        }

        return memberGenreResponseDtos;
    }
}
