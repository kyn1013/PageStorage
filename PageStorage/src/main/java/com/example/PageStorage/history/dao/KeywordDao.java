package com.example.PageStorage.history.dao;

import com.example.PageStorage.entity.Keyword;
import com.example.PageStorage.history.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class KeywordDao {
    private final KeywordRepository keywordRepository;

    public Keyword save(Keyword keyword) {
        Keyword savedKeyword = keywordRepository.save(keyword);
        return savedKeyword;
    }

}
