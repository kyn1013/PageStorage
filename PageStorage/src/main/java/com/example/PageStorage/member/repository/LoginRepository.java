package com.example.PageStorage.member.repository;

import com.example.PageStorage.entity.Login;
import com.example.PageStorage.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByUserLoginId(String userLoginId);
    Boolean existsByUserLoginId(String userLoginId);
}
