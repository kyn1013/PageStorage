package com.example.PageStorage.naverbook;

import lombok.Data;

@Data
public class BooksResponseDto {
    private int display;
    private Item[] items;

    @Data
    static class Item{
        private String title;	//책 제목
        private String author;  //저자명

    }

}
