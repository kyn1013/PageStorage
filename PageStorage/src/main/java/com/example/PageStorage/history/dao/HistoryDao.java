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

    public List<History> slice(Long cursor, Pageable pageable){
        return historyRepository.findByHistorySeqGreaterThan(cursor, pageable);
    }

    public Page<History> findAll(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }

    public History save(History history) {
        return historyRepository.save(history);
    }

    public History find(Long historySeq) {
        History history = historyRepository.findById(historySeq).orElseThrow(() -> new DataNotFoundException("찾을 수 없습니다."));
        return history;
    }

    public History findByMemberNameAndBookName(String memberName, String bookName) {
        History history = historyRepository.findByMemberNameAndBookName(memberName, bookName).orElseThrow(() -> new DataNotFoundException("찾을 수 없습니다."));
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

    public List<History> findAllByCreatedDate() {
        List<History> histories = historyRepository.findAllOrderByCreatedDateDesc();
        return histories;
    }

//    public void delete(String bookName) {
//        History history = historyRepository.findByBookName(bookName).orElseThrow(() -> new DataNotFoundException("찾을 수 없습니다."));
//        historyRepository.delete(history);
//    }

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
        PageRequest pageRequest = PageRequest.of(0, 3); // 상위 3개 결과만 가져오기 위한 페이지 요청
        return historyRepository.findTopBookNamesLast24Hours(startDate, pageRequest);
    }

    public List<String> getTop3Books(){
        Pageable topThree = PageRequest.of(0, 3);
        return historyRepository.findTop3BookNames(topThree);
    }


}
