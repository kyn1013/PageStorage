package com.example.PageStorage.naverbook;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookApiClient bookApiClient;

    @GetMapping("/book/{keyword}")
    public BooksResponseDto goBookPage(@PathVariable("keyword") String keyword){
        return bookApiClient.requestBook(keyword);
    }

}