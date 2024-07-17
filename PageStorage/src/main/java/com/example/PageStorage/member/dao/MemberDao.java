package com.example.PageStorage.member.dao;

import com.example.PageStorage.common.exception.member.MailNotFoundException;
import com.example.PageStorage.common.exception.member.MemberNotFoundException;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberDao {

    private final MemberRepository memberRepository;

    public Member save(Member member) {
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Boolean existsByMail(String mail) {
        return memberRepository.existsByMail(mail);
    }

    public Member find(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));
        return member;
    }


    public Member findMail(String mail) {
        Member member = memberRepository.findByMail(mail).orElseThrow(() -> new MailNotFoundException("메일을 찾을 수 없습니다."));
        return member;
    }


}
