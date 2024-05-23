package com.example.PageStorage.history.dao;

import com.example.PageStorage.common.exception.DataNotFoundException;
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

    //Dao에서 만들게 아님;;
    public Keyword findOrCreate(String keywordName) {
        Keyword keyword;
        try {
            // tagRepository의 findByTagName 메소드를 호출하여 태그를 찾습니다.
            // 태그가 존재하면 반환하고, 존재하지 않으면 DataNotFoundException 예외를 던집니다.
            keyword = keywordRepository.findByKeyword(keywordName).orElseThrow(() -> new DataNotFoundException("존재하지 않는 키워드입니다"));
        } catch (DataNotFoundException e) {
            // 태그가 존재하지 않을 경우, 새로운 태그를 생성하고 저장합니다.
            keyword = new Keyword(keywordName);
            keywordRepository.save(keyword);
        }
        return keyword;
    }

    public void deleteAll(){
        keywordRepository.deleteAll();
    }

    public Keyword find(String keywordName) {
        Keyword keyword = keywordRepository.findByKeyword(keywordName).orElseThrow(() -> new DataNotFoundException("존재하지 않는 키워드입니다"));
        return keyword;
    }

}
