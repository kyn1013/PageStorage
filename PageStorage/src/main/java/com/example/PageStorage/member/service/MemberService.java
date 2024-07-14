package com.example.PageStorage.member.service;

import com.example.PageStorage.entity.Login;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import com.example.PageStorage.member.dto.MemberUpdateRequestDto;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Member saveMember(MemberSaveRequestDto memberSaveRequestDto);

    Member find(String userLoginId);

    Member updateProfile(MemberUpdateRequestDto memberUpdateRequestDto) throws IOException;

}
