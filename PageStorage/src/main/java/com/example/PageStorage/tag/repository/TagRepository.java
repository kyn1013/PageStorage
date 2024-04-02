package com.example.PageStorage.tag.repository;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagName(String tagName);
}
