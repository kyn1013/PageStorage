package com.example.PageStorage.member.dao;

import com.example.PageStorage.entity.Login;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.exception.DataNotFoundException;
import com.example.PageStorage.member.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LoginDao {

    private final LoginRepository loginRepository;

    public Login save(Login login) {
        return loginRepository.save(login);
    }

    public Login findByUserLoginId(String userLoginId) {
        return loginRepository.findByUserLoginId(userLoginId).orElseThrow(() -> new DataNotFoundException("존재하지 않은 회원입니다."));
    }

    public Boolean existsByUserLoginId(String userLoginId) {
        return loginRepository.existsByUserLoginId(userLoginId);
    }

    public void delete(String userLoginId) {
        Login login = loginRepository.findByUserLoginId(userLoginId).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        loginRepository.delete(login);
    }

}
