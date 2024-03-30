package com.example.PageStorage.member.service;

import com.example.PageStorage.entity.Login;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.member.dao.LoginDao;
import com.example.PageStorage.member.dao.MemberDao;
import com.example.PageStorage.member.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberDao memberDao;
    private final LoginDao loginDao;

    @Override
    public Member saveMember(MemberSaveRequestDto memberSaveRequestDto) {
        Member member = Member.builder()
                .name(memberSaveRequestDto.getName())
                .nickName(memberSaveRequestDto.getNickName())
                .phoneNumber(memberSaveRequestDto.getPhoneNumber())
                .mail(memberSaveRequestDto.getMail())
                .build();

        Member savedMember = memberDao.save(member); //회원 객체 반환

        Login login = Login.builder()
                .userLoginId(memberSaveRequestDto.getUserLoginId())
                .userLoginPassword(memberSaveRequestDto.getUserLoginPassword())
                .build();

        login.addMember(savedMember);
        loginDao.save(login); //로그인, 비밀번호 저장
        return savedMember;
    }

    @Override
    public Member find(String userLoginId) {
        Login login = loginDao.findByUserLoginId(userLoginId);
        Member member = memberDao.find(login.getId());
        return member;
    }

    @Override
    public Member find(Long memberSeq) {
        return memberDao.find(memberSeq);
    }

    @Override
    public Member findNickName(String nickName) {
        return memberDao.find(nickName);
    }

    @Override
    public Member findName(String name) {
        return memberDao.findName(name);
    }

    @Override
    public List<Member> findAll() {
        return memberDao.findAll();
    }

    @Override
    public Member update(MemberSaveRequestDto memberSaveRequestDto) {
        Member member = memberDao.findName(memberSaveRequestDto.getName());
        member.changeInfo(memberSaveRequestDto);
        return member;
    }

    @Override
    public Login delete(String userLoginId) {
        return loginDao.delete(userLoginId);
    }

    @Override
    public void delete(Long memberSeq) {
        memberDao.delete(memberSeq);
    }
}
