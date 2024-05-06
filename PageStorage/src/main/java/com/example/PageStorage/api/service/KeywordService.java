package com.example.PageStorage.api.service;

import com.example.PageStorage.history.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public List<String> getKeywordsForMember(String memberMail) {
        return keywordRepository.findKeywordsByMemberMail(memberMail);
    }
}
