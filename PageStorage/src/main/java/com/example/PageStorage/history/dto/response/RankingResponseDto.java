package com.example.PageStorage.history.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RankingResponseDto {
    private String title;	//책 제목
    private String image; //이미지

    @Builder
    public RankingResponseDto (String title, String image) {
        this.title = title;
        this.image = image;
    }

}
