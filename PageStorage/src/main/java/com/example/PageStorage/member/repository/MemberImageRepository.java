package com.example.PageStorage.member.repository;

import com.example.PageStorage.entity.HistoryImage;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.entity.MemberImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
    MemberImage findByMemberMemberSeq(Long memberId);
}
