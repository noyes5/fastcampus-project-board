package com.mirishop.userservice.infrastructure.security;

import com.mirishop.userservice.common.exception.CustomException;
import com.mirishop.userservice.common.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String CLAIM_EMAIL = "email";

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration-time}")
    private Duration accessTokenExpirationTime;

    @Getter
    @Value("${jwt.refresh-expiration-time}")
    private Duration refreshTokenExpirationTime;

    private SecretKey key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String userEmail) {
        return createToken(userEmail, accessTokenExpirationTime.toMillis());
    }

    public String generateRefreshToken(String userEmail) {
        return createToken(userEmail, refreshTokenExpirationTime.toMillis());
    }

    private String createToken(String userEmail, long expirationTime) {
        Date now = new Date();
        return Jwts.builder()
                .claim(CLAIM_EMAIL, userEmail)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        validateToken(token);
        return parseClaims(token).get(CLAIM_EMAIL, String.class);
    }

    public Long extractTokenExpiration(String token) {
        validateToken(token);
        Date expirationDate = parseClaims(token).getExpiration();
        return expirationDate.getTime() / 1000; // 밀리초를 초로 변환
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = parseToken(token);
            return !isTokenExpired(claims);
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch (SignatureException | MalformedJwtException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN_FORMAT);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    private Claims parseClaims(String token) {
        return parseToken(token).getBody();
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    private boolean isTokenExpired(Jws<Claims> claims) {
        return claims.getBody().getExpiration().before(new Date());
    }
}
