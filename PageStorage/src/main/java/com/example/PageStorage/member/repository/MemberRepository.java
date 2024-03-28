package com.example.PageStorage.member.repository;

import com.example.PageStorage.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
