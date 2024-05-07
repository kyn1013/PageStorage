package com.example.PageStorage.history.repository;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.HistoryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryImageRepository extends JpaRepository<HistoryImage, Long> {
    void deleteByHistoryHistorySeq(Long historyId);
}
