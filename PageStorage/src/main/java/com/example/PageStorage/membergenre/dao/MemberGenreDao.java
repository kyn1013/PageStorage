package com.example.PageStorage.membergenre.dao;

import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.entity.MemberGenre;
import com.example.PageStorage.membergenre.repository.MemberGenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberGenreDao {

    private final MemberGenreRepository memberGenreRepository;

    public MemberGenre save(MemberGenre memberGenre) {
        return memberGenreRepository.save(memberGenre);
    }

    public MemberGenre findById(Long memberGenreSeq) {
        return memberGenreRepository.findById(memberGenreSeq).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public List<MemberGenre> findByMember(Member member) {
        return memberGenreRepository.findByMember(member);
    }

    public List<MemberGenre> findByGenre(Genre genre) {
        return memberGenreRepository.findByGenre(genre);
    }

    public List<MemberGenre> findAll() {
        return memberGenreRepository.findAll();
    }

    public MemberGenre findByMemberAndGenre(Member member, Genre genre) {
        return memberGenreRepository.findByMemberAndGenre(member, genre);
    }

    public void delete(MemberGenre memberGenre) {
        memberGenreRepository.delete(memberGenre);
    }
}
