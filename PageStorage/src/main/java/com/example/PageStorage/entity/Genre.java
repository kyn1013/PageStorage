package com.example.PageStorage.entity;

import com.example.PageStorage.genre.dto.GenreUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genre")
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_seq")
    private Long genreSeq;

    @Column(name = "genre_name")
    private String genreName;

//    @Column(name = "novel") //소설
//    private Boolean novel = false;
//
//    @Column(name = "poetry") //시
//    private Boolean poetry = false;
//
//    @Column(name = "essay") //에세이
//    private Boolean essay = false;
//
//    @Column(name = "romance") //로맨스
//    private Boolean romance = false;
//
//    @Column(name = "thriller") //스릴러
//    private Boolean thriller = false;
//
//    @Column(name = "mystery") //추리
//    private Boolean mystery = false;
//
//    @Column(name = "selfHelp") //자기계발
//    private Boolean selfHelp = false;
//
//    @Column(name = "fantasy") //판타지
//    private Boolean fantasy = false;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<MemberGenre> memberGenres = new ArrayList<>();

    @Builder
    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public void changeInfo (GenreUpdateRequestDto genreUpdateRequestDto) {
        this.genreName = genreUpdateRequestDto.getGenreName();
    }

}
