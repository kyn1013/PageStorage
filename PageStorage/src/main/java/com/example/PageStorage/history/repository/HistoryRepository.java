package com.example.PageStorage.history.repository;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    //커서 기반 페이징
    List<History> findAllByOrderByHistorySeqDescCreatedDateDesc(Pageable pageable);
    List<History> findByHistorySeqLessThanOrderByHistorySeqDescCreatedDateDesc(@Param("historySeq") long historySeq, Pageable pageable);

    List<History> findByBookName(String bookName);
    List<History> findByMember(Member member);

    @Query("SELECT h.bookName " +
            "FROM History h " +
            "WHERE h.createdDate >= :startDate " +
            "GROUP BY h.bookName " +
            "ORDER BY COUNT(h.bookName) DESC")
    List<String> findTopBookNamesLast24Hours(@Param("startDate") LocalDateTime startDate, Pageable pageable);

}
