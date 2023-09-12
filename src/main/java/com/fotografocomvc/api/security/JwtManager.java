package com.fotografocomvc.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import static com.fotografocomvc.api.security.SecurityConstants.ACCESS_TOKEN_EXPIRATION;
import static com.fotografocomvc.api.security.SecurityConstants.REFRESH_TOKEN_EXPIRATION;

@Service
public class JwtManager {

    public String generateAccessJWT(Authentication authentication){
        return generateToken(authentication, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshJWT(Authentication authentication){
        return generateToken(authentication, REFRESH_TOKEN_EXPIRATION);
    }

    public String generateToken (Authentication authentication, Long expirationTime){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
    }

    public String generateTokenByEmail (String email, Long expirationTime){
        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
    }

    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(SecurityConstants.JWT_SECRET)
                    .parseClaimsJws(token);

            return true;
        } catch (Exception exception) {
            throw new AuthenticationCredentialsNotFoundException("Jwt was either expired or incorrect");
        }

    }
}
