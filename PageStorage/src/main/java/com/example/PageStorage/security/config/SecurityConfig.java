package com.example.PageStorage.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean //암호화 방식을 변경할 수 있기 때문에 인터페이스로 선언, BCryptPasswordEncoder의 인터페이스
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

//        http
//                .authorizeHttpRequests((auth) -> auth //순서를 잘 기켜야 ㅗㅅ세
//                        .requestMatchers("/", "/members/login", "/members/join", "joinProc").permitAll()
//                        .requestMatchers("/admin").hasRole("ADMIN")
////                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
//                        .anyRequest().authenticated() //로그인한 사용자만 허용
//                );

        http
                .authorizeHttpRequests((auth) -> auth
                        .anyRequest().permitAll() // 모든 요청에 대해 접근 허용
                );

        http
                .formLogin((auth) -> auth.loginPage("/members/login") //admin page에 접근하려고 할때 로그인 페이지 뜨게 함
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/histories/all")
                );

        http
                .csrf((auth) -> auth.disable());

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) //다중로그인 허용 개수
                        .maxSessionsPreventsLogin(true)); //동시접속수 초과하면 로그인 못하게 차단

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()); //보안

        http
                .logout((auth) -> auth.logoutUrl("members/logout")
                        .logoutSuccessUrl("/members/login"));

        return http.build();
    }
}
