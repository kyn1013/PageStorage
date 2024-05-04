package com.example.PageStorage.history.repository;

import com.example.PageStorage.entity.History;
import com.example.PageStorage.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
