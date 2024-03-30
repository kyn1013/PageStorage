package com.example.PageStorage.membergenre.dao;

import com.example.PageStorage.entity.MemberGenre;
import com.example.PageStorage.membergenre.repository.MemberGenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberGenreDao {

    private final MemberGenreRepository memberGenreRepository;

    public MemberGenre save(MemberGenre memberGenre) {
        return memberGenreRepository.save(memberGenre);
    }
    
}
