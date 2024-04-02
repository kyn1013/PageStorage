package com.example.PageStorage.historytag.service;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.HistoryTag;
import com.example.PageStorage.history.dao.HistoryDao;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import com.example.PageStorage.historytag.dao.HistoryTagDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HistoryTagService {
    private final HistoryTagDao historyTagDao;

    public void delete(Long historyTagSeq) {
        historyTagDao.delete(historyTagSeq);
    }
}
