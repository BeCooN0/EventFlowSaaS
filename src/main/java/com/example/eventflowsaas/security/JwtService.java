package com.example.eventflowsaas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.expiration}")
    private long expiration;
    private SecretKey key;
    @Value("${jwt.secret}")
    private String secret;
    @PostConstruct
    void init(){
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(UserDetails userDetails, String tenant){
        return Jwts
                .builder()
                .signWith(key)
                .claims(Map.of("tenant", tenant))
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public Claims getAllClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsFunction){
        Claims allClaims = getAllClaims(token);
        return claimsFunction.apply(allClaims);
    }

    public String getSubject(String token){
        return getClaim(token, Claims::getSubject);
    }
    public Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token, UserDetails userDetails){
        Date expirationDate = getClaim(token, Claims::getExpiration);
        String username = getSubject(token);
        if (!expirationDate.after(new Date()) || !username.equals(userDetails.getUsername())){
            return false;
        }
        return true;
    }

    public String getTenant(String token) {
        return getClaim(token, tenant-> tenant.get("tenant", String.class));
    }
}
