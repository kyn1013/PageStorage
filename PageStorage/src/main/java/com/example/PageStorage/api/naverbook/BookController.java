package com.example.PageStorage.api.naverbook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookApiClient bookApiClient;

    /*
    http://localhost:8080/book/책체목 으로 들어가면 조회 가능
     */
    @GetMapping("/book/{keyword}")
    public BooksResponseDto goBookPage(@PathVariable("keyword") String keyword){
//        String keyword = "과자";
        return bookApiClient.requestBook(keyword);
    }




}