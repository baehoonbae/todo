package com.ssafy.edu.todo.config;

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/user/**").permitAll()  // 회원가입과 로그인 경로는 인증 없이 접근 허용
                    .anyRequest().authenticated()  // 나머지 경로는 인증 필요
            )
            .csrf(csrf -> csrf.disable());  // CORS 문제 해결을 위해 CSRF 비활성화 (개발 환경에서만)

        return http.build();
    }
}
