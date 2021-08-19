package com.mr.myrecord.security.config;

import com.mr.myrecord.security.entity.JwtUtil;
import com.mr.myrecord.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //@Value("${jwt.secret}")
    //TODO: secret 암호화하기.
    private String secret = "12345678901234567890123456789012";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter filter = new JwtAuthenticationFilter(
                //Configuration이므로 메소드 콜로 호출
                authenticationManager(), jwtUtil()
        );

        http
                .cors().disable() // 보안에 관한 것
                .csrf().disable()
                .formLogin().disable() // 로그인 폼을 없애줌
                .headers().frameOptions().disable() // h2-console 헤더 보이게 하기
                // 필터 추가
                // and()로 초기화
                .and()
                .addFilter(filter)
                .sessionManagement() // 세션에 대해 관리
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 정책

        //TODO: antMatcher 처리 필요
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secret);
    }
}
