package com.example.PageStorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history_tag")
@Entity
public class HistoryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_tag_seq")
    private Long historyTagSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_seq")
    private History history;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_seq")
    private Tag tag;

    public void addHistory(History history) {
        this.history = history;
    }

    public void addTag(Tag tag) {
        this.tag = tag;
    }

    @Builder
    public HistoryTag(History history, Tag tag) {
        this.history = history;
        this.tag = tag;
    }
}
