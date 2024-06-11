package com.example.PageStorage.history.repository;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Keyword;
import com.example.PageStorage.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k.keyword FROM Keyword k " +
            "JOIN k.historyKeywords hk " +
            "JOIN hk.history h " +
            "JOIN h.member m " +
            "WHERE m.mail = :memberMail")
    Set<String> findKeywordsByMemberMail(String memberMail);

    Optional<Keyword> findByKeyword(String keyword);
}
