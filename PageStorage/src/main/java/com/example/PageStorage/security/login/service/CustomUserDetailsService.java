package com.example.PageStorage.security.login.service;

import com.example.PageStorage.entity.Login;
import com.example.PageStorage.security.login.dao.LoginDao;
import com.example.PageStorage.security.login.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginDao loginDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Login userData = loginDao.findByUserLoginId(username).getLogin();

        if (userData != null) {

            return new CustomUserDetails(userData);
        }

        return null;
    }
}
