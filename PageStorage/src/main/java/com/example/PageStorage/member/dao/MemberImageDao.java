package com.example.PageStorage.member.dao;

import com.example.PageStorage.entity.HistoryImage;
import com.example.PageStorage.entity.MemberImage;
import com.example.PageStorage.member.repository.MemberImageRepository;
import com.example.PageStorage.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberImageDao {

    private final MemberImageRepository memberImageRepository;

    public MemberImage save(MemberImage memberImage) {
        return memberImageRepository.save(memberImage);
    }
}
