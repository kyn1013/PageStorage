package com.example.PageStorage.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_seq")
    private Long boardSeq;

    @Column(name = "title")
    private String title;

    @Column(columnDefinition = "TEXT", name = "board_content")
    private String boardContent;

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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardTag> boardTags = new ArrayList<>();

}
