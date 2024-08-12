package com.mirishop.user.auth.infrastructure;

import com.mirishop.user.common.exception.ErrorCode;
import com.mirishop.user.common.exception.JwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long accessTokenExpirationTime;

    @Value("${security.jwt.refresh-expiration-time}")
    private long refreshTokenExpirationTime;

    private SecretKey key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateAccessToken(String userEmail, Long userId) {
        Date now = new Date();
        return createToken(userEmail, userId, now, accessTokenExpirationTime);
    }

    public String generateRefreshToken(String userEmail, Long userId) {
        Date now = new Date();
        return createToken(userEmail, userId, now, accessTokenExpirationTime);
    }

    public String createToken(String userEmail, Long userId, Date now, long expirationTime) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", userEmail)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        if (!validateToken(token)) {
            throw new JwtTokenException(ErrorCode.INVALID_TOKEN);
        }
        return parseClaims(token).get("email", String.class);
    }

    public String extractEmailFromRefreshToken(String token) {
        if (!validateToken(token)) {
            throw new JwtTokenException(ErrorCode.INVALID_TOKEN);
        }
        // 토큰에서 subject에 있는 email 반환
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtTokenException(ErrorCode.INVALID_TOKEN);
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
