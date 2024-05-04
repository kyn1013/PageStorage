package com.example.PageStorage.entity;

import com.example.PageStorage.tag.dto.TagUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "keyword")
@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_seq")
    private Long keywordSeq;

    @Column(name = "keyword")
    private String keyword;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
    private Set<HistoryKeyword> historyKeywords = new HashSet<>();

    @Builder
    public Keyword(String keyword) {
        this.keyword = keyword;
    }

}
