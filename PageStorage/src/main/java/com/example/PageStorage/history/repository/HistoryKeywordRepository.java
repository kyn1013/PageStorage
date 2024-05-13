package com.example.PageStorage.history.repository;

import com.example.PageStorage.entity.HistoryKeyword;
import com.example.PageStorage.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryKeywordRepository extends JpaRepository<HistoryKeyword, Long> {
    List<HistoryKeyword> findByHistoryHistorySeq(Long historySeq); 
}
