package com.example.PageStorage.history.dao;

import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.common.exception.keyword.KeywordNotFoundException;
import com.example.PageStorage.entity.Keyword;
import com.example.PageStorage.entity.Tag;
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

    public Keyword findOrCreate(String keywordName) {
        Keyword keyword;
        try {
            keyword = keywordRepository.findByKeyword(keywordName).orElseThrow(() -> new KeywordNotFoundException("존재하지 않는 키워드입니다"));
        } catch (KeywordNotFoundException e) {
            keyword = new Keyword(keywordName);
            keywordRepository.save(keyword);
        }
        return keyword;
    }

    public void deleteAll(){
        keywordRepository.deleteAll();
    }

    public Keyword find(String keywordName) {
        Keyword keyword = keywordRepository.findByKeyword(keywordName).orElseThrow(() -> new KeywordNotFoundException("존재하지 않는 키워드입니다"));
        return keyword;
    }

}
