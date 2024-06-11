package com.example.PageStorage.security.login.dto;

import com.example.PageStorage.entity.Login;
import com.example.PageStorage.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Login userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userEntity.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getUserLoginPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserLoginId();
    }

    public String getUserLoginId() {
        return userEntity.getUserLoginId();
    }

    public String getNickname() {
        return userEntity.getMember().getNickName();
    }

    public String getMail() {
        return userEntity.getMember().getMail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
