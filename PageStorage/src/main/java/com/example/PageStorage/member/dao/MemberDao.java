package com.example.PageStorage.member.dao;

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


//    public Member findByMemberId(String memberId) {
//        return memberRepository.findByMemberId(memberId).orElseThrow(() -> new DataNotFoundException("존재하지 않은 회원입니다."));
//    }
    public Member find(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        return member;

    }

    public Member find(String nickName) {
        Member member = memberRepository.findByNickName(nickName).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        return member;
    }

    public Member findName(String name) {
        Member member = memberRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다."));
        return member;
    }

    public List<Member> findAll() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public void delete(Long memberSeq) {
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        memberRepository.delete(member);
    }

//    public void delete(String userLoginId) {
//        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
//        memberRepository.delete(member);
//    }



//    public Boolean existsByMemberId(String memberId) {
//        return memberRepository.existsByMemberId(memberId);
//    }
}
