package com.example.PageStorage.history.dao;

import com.example.PageStorage.entity.HistoryKeyword;
import com.example.PageStorage.entity.HistoryTag;
import com.example.PageStorage.history.repository.HistoryKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HistoryKeywordDao {

    private final HistoryKeywordRepository historyKeywordRepository;

    public HistoryKeyword save(HistoryKeyword historyKeyword) {
        return historyKeywordRepository.save(historyKeyword);
    }



    public List<HistoryKeyword> findByHistorySeq(Long historySeq) {
        return historyKeywordRepository.findByHistoryHistorySeq(historySeq);
    }

    public List<HistoryKeyword> findAll() {
        return historyKeywordRepository.findAll();
    }

    public void deleteHistorySeq(Long historySeq) {
        List<HistoryKeyword> historyKeywords = historyKeywordRepository.findByHistoryHistorySeq(historySeq);
        historyKeywordRepository.deleteAll(historyKeywords);
    }

}
