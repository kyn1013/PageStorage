package com.example.PageStorage.historytag.repository;


import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.HistoryTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryTagRepository extends JpaRepository<HistoryTag, Long> {
    List<HistoryTag> findByHistoryHistorySeq(Long historySeq);
}
