package com.example.PageStorage.membergenre.service;

import com.example.PageStorage.entity.Genre;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.entity.MemberGenre;
import com.example.PageStorage.genre.dao.GenreDao;
import com.example.PageStorage.genre.dto.GenreRequestDto;
import com.example.PageStorage.member.dao.MemberDao;
import com.example.PageStorage.membergenre.dao.MemberGenreDao;
import com.example.PageStorage.membergenre.dto.MemberGenreRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberGenreService {

    private final MemberGenreDao memberGenreDao;
    private final MemberDao memberDao;
    private final GenreDao genreDao;

    public MemberGenre saveMemberGenre(MemberGenreRequestDto memberGenreRequestDto) {
        Member member = memberDao.findName(memberGenreRequestDto.getMemberName());
        Genre genre = genreDao.find(memberGenreRequestDto.getGenreName());

        MemberGenre memberGenre = MemberGenre.builder()
                .member(member)
                .genre(genre)
                .build();

        return memberGenreDao.save(memberGenre);
    }

    public List<Genre> findGenresByMemberName(String memberName) {
        Member member = memberDao.findName(memberName);

        List<Genre> preferredGenres = new ArrayList<>();
        List<MemberGenre> memberGenres = memberGenreDao.findByMember(member);
        for (MemberGenre memberGenre : memberGenres) {
            preferredGenres.add(memberGenre.getGenre());
        }
        return preferredGenres;
    }

    public List<Member> findMembersByGenreName(String genreName) {
        Genre genre = genreDao.find(genreName);

        List<Member> members = new ArrayList<>();
        List<MemberGenre> memberGenres = memberGenreDao.findByGenre(genre);
        for (MemberGenre memberGenre : memberGenres) {
            members.add(memberGenre.getMember());
        }
        return members;
    }

    public List<MemberGenre> findAll() {
        return memberGenreDao.findAll();
    }

    public void delete(MemberGenreRequestDto memberGenreRequestDto) {
        Member member = memberDao.findName(memberGenreRequestDto.getMemberName());
        Genre genre = genreDao.find(memberGenreRequestDto.getGenreName());

        MemberGenre memberGenre = memberGenreDao.findByMemberAndGenre(member, genre);
        memberGenreDao.delete(memberGenre);
    }



}
