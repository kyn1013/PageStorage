package com.example.PageStorage.recommendation.service;

import com.example.PageStorage.recommendation.api.naverbook.BookApiClient;
import com.example.PageStorage.recommendation.api.naverbook.BooksResponseDto;
import com.example.PageStorage.common.exception.recommendation.MaxRetriesExceededException;
import com.example.PageStorage.common.exception.recommendation.ThreadInterruptedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecommendationService {

    private final BookApiClient bookApiClient;

    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY_MS = 1000;

    //백오프
    public BooksResponseDto requestBookWithRetry(String bookTitle) {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                return bookApiClient.requestBook(bookTitle);
            } catch (ArrayIndexOutOfBoundsException e) {
                attempt++;
                if (attempt >= MAX_RETRIES) {
                    throw e;
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS * attempt); // 점점 증가하는 대기 시간
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new ThreadInterruptedException("백오프 지연동안 스레드가 인터럽트 되었습니다.", ie);
                }
            }
        }
        throw new MaxRetriesExceededException("최대 재시도 횟수를 초과했습니다.");
    }


    private Map<String, BooksResponseDto> cache = new HashMap<>();

    //캐싱
    public BooksResponseDto requestBookWithCacheAndRetry(String bookTitle) {
        if (cache.containsKey(bookTitle)) {
            return cache.get(bookTitle);
        }
        BooksResponseDto response = requestBookWithRetry(bookTitle);
        cache.put(bookTitle, response);
        return response;
    }
}
