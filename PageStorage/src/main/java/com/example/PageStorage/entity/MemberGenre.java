package com.example.PageStorage.entity;

import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_genre")
@Entity
public class MemberGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_genre_seq")
    private Long memberGenreSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_seq")
    private Genre genre;

    public void addMember(Member member) {
        this.member = member;
    }

    public void addGenre(Genre genre) {
        this.genre = genre;
    }

    @Builder
    public MemberGenre(Member member, Genre genre) {
        this.member = member;
        this.genre = genre;
    }




}
