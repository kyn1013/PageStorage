package com.example.PageStorage.history.dao;

import com.example.PageStorage.entity.Member;
import com.example.PageStorage.history.repository.HistoryRepository;
import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.entity.History;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HistoryDao {

    private final HistoryRepository historyRepository;

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


}
