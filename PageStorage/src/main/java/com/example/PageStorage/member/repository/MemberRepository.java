package com.example.PageStorage.member.repository;

import com.example.PageStorage.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMail(String mail);

    Boolean existsByMail(String mail);
}
