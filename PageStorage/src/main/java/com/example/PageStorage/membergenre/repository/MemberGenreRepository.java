package com.example.PageStorage.membergenre.repository;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.entity.MemberGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGenreRepository extends JpaRepository<MemberGenre, Long> {
    List<MemberGenre> findByMember(Member member);

    List<MemberGenre> findByGenre(Genre genre);

    MemberGenre findByMemberAndGenre(Member member, Genre genre);
}
