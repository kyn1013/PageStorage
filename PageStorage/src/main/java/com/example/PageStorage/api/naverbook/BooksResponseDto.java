package com.example.PageStorage.api.naverbook;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooksResponseDto {
    private int display;
    private Item[] items;

    @Getter
    @Setter
    public static class Item{
        private String title;	//책 제목
        private String author;  //저자명
        private String image; //이미지
        private String publisher; //출판사
        private String description; //줄거리

    }

}
