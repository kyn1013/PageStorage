package com.example.PageStorage.history.repository;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByBookName(String bookName);
    List<History> findByMember(Member member);
    @Query("select h from History h where h.member.name = :memberName and h.bookName = :bookName")
    Optional<History> findByMemberNameAndBookName(@Param("memberName") String memberName, @Param("bookName") String bookName);

    @Query("SELECT h FROM History h ORDER BY h.createdDate DESC")
    List<History> findAllOrderByCreatedDateDesc();
}
