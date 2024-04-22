package com.example.PageStorage.security.login.dao;

import com.example.PageStorage.entity.Login;
import com.example.PageStorage.common.exception.DataNotFoundException;
import com.example.PageStorage.entity.Member;
import com.example.PageStorage.security.login.repository.LoginRepository;
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

    public Member findByUserLoginId(String userLoginId) {
        Login login = loginRepository.findByUserLoginId(userLoginId).orElseThrow(() -> new DataNotFoundException("존재하지 않은 회원아이디!!입니다."));
        return login.getMember();
    }

    public Boolean existsByUserLoginId(String userLoginId) {
        return loginRepository.existsByUserLoginId(userLoginId);
    }

    public Login delete(String userLoginId) {
        Login login = loginRepository.findByUserLoginId(userLoginId).orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원입니다."));
        loginRepository.delete(login);
        return login;
    }

}
