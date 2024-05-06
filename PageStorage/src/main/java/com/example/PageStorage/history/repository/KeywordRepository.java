package com.example.PageStorage.history.repository;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query("SELECT k.keyword FROM Keyword k " +
            "JOIN k.historyKeywords hk " +
            "JOIN hk.history h " +
            "JOIN h.member m " +
            "WHERE m.mail = :memberMail")
    List<String> findKeywordsByMemberMail(String memberMail);
}
