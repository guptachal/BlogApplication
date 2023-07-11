package com.portfolio.blog.Blog.security;

import com.portfolio.blog.Blog.exception.BlogAPIException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value(("${app-jwt-expiration-milliseconds}"))
    private long jwtExpirationDate;

    //Generate JWT Token
    public String generateToken(Authentication authentication){
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        claims.put("role",authentication.getAuthorities());
        Date  currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecret));

    }
    // Get username from the JTW token
    public String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJwt(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }
        catch (MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid Jwt Token");
        }
    }
}
