package com.example.PageStorage.history.dao;

import com.example.PageStorage.entity.Member;
import com.example.PageStorage.history.repository.HistoryRepository;
import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.entity.History;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HistoryDao {

    private final HistoryRepository historyRepository;

    public List<History> findAllByOrderByHistorySeqDescCreatedDateDesc(Pageable pageable) {
        List<History> histories = historyRepository.findAllByOrderByHistorySeqDescCreatedDateDesc(pageable);
        return histories;
    }

    public List<History> findByHistorySeqLessThanOrderByHistorySeqDescCreatedDateDesc(@Param("historySeq") long historySeq, Pageable pageable) {
        List<History> histories = historyRepository.findByHistorySeqLessThanOrderByHistorySeqDescCreatedDateDesc(historySeq, pageable);
        return histories;
    }

    public History save(History history) {
        return historyRepository.save(history);
    }

    public History find(Long historySeq) {
        History history = historyRepository.findById(historySeq).orElseThrow(() -> new DataNotFoundException("찾을 수 없습니다."));
        return history;
    }

    public List<History> find(String bookName) {
        List<History> histories = historyRepository.findByBookName(bookName);
        return histories;
    }

    public List<History> findAll() {
        List<History> histories = historyRepository.findAll();
        return histories;
    }

    public void delete(Long historySeq) {
        History history = historyRepository.findById(historySeq).orElseThrow(() -> new DataNotFoundException("찾을 수 없습니다."));
        historyRepository.delete(history);
    }

    public void delete(History history) {
        historyRepository.delete(history);
    }

    public List<History> findByMember(Member member) {
        List<History> histories = historyRepository.findByMember(member);
        return histories;
    }

    public List<String> findTopBookNamesLast24Hours(LocalDateTime startDate){
        PageRequest pageRequest = PageRequest.of(0, 3); // 상위 3개 결과만 가져오기 위한 페이지
        return historyRepository.findTopBookNamesLast24Hours(startDate, pageRequest);
    }



}
