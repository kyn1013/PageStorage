package com.example.PageStorage.historytag.dao;

import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.entity.HistoryTag;
import com.example.PageStorage.historytag.repository.HistoryTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HistoryTagDao {

    private final HistoryTagRepository historyTagRepository;

    public HistoryTag save(HistoryTag historyTag) {
        return historyTagRepository.save(historyTag);
    }

    public List<HistoryTag> findAll() {
        return historyTagRepository.findAll();
    }

    public void delete(Long historyTagSeq) {
        HistoryTag historyTag = historyTagRepository.findById(historyTagSeq).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        historyTagRepository.delete(historyTag);
    }

    public void deleteHistorySeq(Long historySeq) {
        List<HistoryTag> historyTags = historyTagRepository.findByHistoryHistorySeq(historySeq);
        historyTagRepository.deleteAll(historyTags);
    }

}
