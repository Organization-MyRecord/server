package com.mr.myrecord.security.entity;

import com.mr.myrecord.exception.MalformedJwt;
import io.jsonwebtoken.*;
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
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token) //sign이 포함된 jwt
                    .getBody();
            return body;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new MalformedJwt("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new MalformedJwt("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new MalformedJwt("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new MalformedJwt("JWT 토큰이 잘못되었습니다.");
        }

    }
}
