package com.example.PageStorage.history.dao;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.HistoryImage;
import com.example.PageStorage.history.repository.HistoryImageRepository;
import com.example.PageStorage.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HistoryImageDao {

    private final HistoryImageRepository historyImageRepository;

    public HistoryImage save(HistoryImage historyImage) {
        return historyImageRepository.save(historyImage);
    }

    public HistoryImage findByHistorySeq(Long historySeq) {
        return historyImageRepository.findByHistoryHistorySeq(historySeq);
    }

    public void deleteByHistoryId(Long historyId) {
        historyImageRepository.deleteByHistoryHistorySeq(historyId);
    }

    public void delete(Long historyImageSeq) {
        historyImageRepository.deleteById(historyImageSeq);
    }

}
