package com.example.PageStorage.api.naverbook;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultResponseDto {
    private String title;	//책 제목
    private String author;  //저자명
    private String image; //이미지
    private String publisher; //출판사
    private String description; //줄거리

    @Builder
    public ResultResponseDto (String title, String author, String image, String publisher, String description) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.publisher = publisher;
        this.description = description;
    }
}
