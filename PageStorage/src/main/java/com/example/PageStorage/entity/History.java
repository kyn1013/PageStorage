package com.example.PageStorage.entity;

import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;
import com.example.PageStorage.history.dto.HistoryRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "history")
@Entity
public class History extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_seq")
    private Long historySeq;

    @Column(name = "book_name")
    private String bookName;

    @Column(columnDefinition = "TEXT", name = "history_content")
    private String historyContent;

    @Column(name = "phrase")
    private String phrase;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "application_to_life")
    private String applicationToLife;

    @Column(name = "book_recommender")
    private String bookRecommender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @OneToOne(mappedBy = "history", cascade = CascadeType.ALL)
    private HistoryImage historyImage;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private Set<HistoryTag> historyTags = new HashSet<>();

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
    private Set<HistoryKeyword> historyKeywords = new HashSet<>();

    @Builder
    public History(String bookName, String historyContent, String phrase,
                              String difficulty, String applicationToLife, String bookRecommender){
        this.bookName = bookName;
        this.historyContent = historyContent;
        this.phrase = phrase;
        this.difficulty = difficulty;
        this.applicationToLife = applicationToLife;
        this.bookRecommender = bookRecommender;
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void addHistoryImage(HistoryImage historyImage) {
        this.historyImage = historyImage;
    }

    public void changeInfo (HistoryRequestDto historyRequestDto) {
        this.bookName = historyRequestDto.getBookName();
        this.historyContent = historyRequestDto.getHistoryContent();
        this.phrase = historyRequestDto.getPhrase();
        this.difficulty = historyRequestDto.getDifficulty();
        this.applicationToLife = historyRequestDto.getApplicationToLife();
        this.bookRecommender = historyRequestDto.getBookRecommender();
    }

}
