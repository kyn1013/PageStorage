package com.example.PageStorage.member.service;

import com.example.PageStorage.entity.Login;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;

import java.util.List;

public interface MemberService {
    Member saveMember(MemberSaveRequestDto memberSaveRequestDto);
    Member find(String userLoginId);

    Member findNickName(String nickName);

    Member findName(String name);

    Member find(Long memberSeq);

    List<Member> findAll();

    Member update(MemberSaveRequestDto memberSaveRequestDto);

    Login delete(String userLoginid);

    void delete(Long memberSeq);
}
