package com.mr.myrecord.security.filter;

import com.mr.myrecord.exception.UnAuthorizationException;
import com.mr.myrecord.security.entity.JwtUtil;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    /**
     * 실제로 JWT 분석 필요
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        // Header에서 데이터 얻기
        Authentication authentication = getAuthentication(request);

        // 토큰이 없다면
        if(authentication != null) {
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
        }

        chain.doFilter(request, response); // 다음 작업으로 계속 연결
    }

    // 에러처리 메소드
    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization"); // 헤더에서 데이터 얻기
        if (token == null) {
            //throw new UnAuthorizationException("인가되지 않은 사용자");
            return null;
        }

        //JwtUtil에서 claims 얻기
        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));
        // "Bearer ".length() 이만큼은 제거!
        // 실제로는 Authorization: Bearer TOKEN.**** 이므로 Bearer를 뺴줘야함
//
//        if (claims == null) {
//            //throw new UnAuthorizationException("인가되지 않은 사용자");
//        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(claims, null);
        // 1. principal, 2. credentials
        return authentication;
    }
}
