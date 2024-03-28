package com.example.PageStorage.member.service;

import com.example.PageStorage.entity.Member;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;

import java.util.List;

public interface MemberService {
    Member saveMember(MemberSaveRequestDto memberSaveRequestDto);
    Member find(String userLoginId);

    Member find(Long memberSeq);

    List<Member> findAll();

    Member update(MemberSaveRequestDto memberSaveRequestDto);

    void delete(String memberId);

    void delete(Long memberSeq);
}
