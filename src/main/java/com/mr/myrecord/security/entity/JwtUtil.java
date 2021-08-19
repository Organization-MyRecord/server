package com.mr.myrecord.security.entity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

public class JwtUtil {

    private Key key;

    /**
     * JWT 사용
     */
    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(String email) {
        // JJWT 사용!
        return Jwts.builder()
                // claim 데이터 payload에 추가
                .claim("email", email)
                //TODO:토큰 만료시간 추가

                // 서명 추가
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token) //sign이 포함된 jwt
                .getBody();
    }
}
