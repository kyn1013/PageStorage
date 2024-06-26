package com.example.PageStorage.history.repository;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Slice<History> findByHistorySeqGreaterThan(Long cursor, Pageable pageable);


    List<History> findByBookName(String bookName);
    List<History> findByMember(Member member);


    @Query("select h from History h where h.member.name = :memberName and h.bookName = :bookName")
    Optional<History> findByMemberNameAndBookName(@Param("memberName") String memberName, @Param("bookName") String bookName);

    @Query("SELECT h FROM History h ORDER BY h.createdDate DESC")
    List<History> findAllOrderByCreatedDateDesc();

    @Query("SELECT h.bookName " +
            "FROM History h " +
            "WHERE h.createdDate >= :startDate " +
            "GROUP BY h.bookName " +
            "ORDER BY COUNT(h.bookName) DESC")
    List<String> findTopBookNamesLast24Hours(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    @Query("SELECT h.bookName " +
            "FROM History h " +
            "GROUP BY h.bookName " +
            "ORDER BY COUNT(h.bookName) DESC")
    List<String> findTop3BookNames(Pageable pageable);
}
