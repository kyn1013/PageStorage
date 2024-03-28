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
@Table(name = "genre")
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_seq")
    private Long genreSeq;

    @Column(name = "novel") //소설
    private String novel;

    @Column(name = "poetry") //시
    private String poetry;

    @Column(name = "essay") //에세이
    private String essay;

    @Column(name = "romance") //로맨스
    private String romance;

    @Column(name = "thriller") //스릴러
    private String thriller;

    @Column(name = "mystery") //추리
    private String mystery;

    @Column(name = "selfHelp") //자기계발
    private String selfHelp;

    @Column(name = "fantasy") //판타지
    private String fantasy;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<MemberGenre> memberGenres = new ArrayList<>();
}
