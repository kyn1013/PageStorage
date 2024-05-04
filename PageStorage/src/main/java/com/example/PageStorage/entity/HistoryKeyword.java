package com.example.PageStorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history_keyword")
@Entity
public class HistoryKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_keyword_seq")
    private Long historyKeywordSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_seq")
    private History history;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_seq")
    private Keyword keyword;

    public void addHistory(History history) {
        this.history = history;
    }

    public void addKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    @Builder
    public HistoryKeyword(History history, Keyword keyword) {
        this.history = history;
        this.keyword = keyword;
    }
}
