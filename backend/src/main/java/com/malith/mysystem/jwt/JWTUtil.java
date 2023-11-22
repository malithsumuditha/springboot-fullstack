package com.malith.mysystem.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
public class JWTUtil {
    private static final String SECRET_KEY = "malithsumuditha123456789_malithsumuditha123456789_malithsumuditha123456789_";

    public String issueToken(String subject, String ...scopes){
        return issueToken(subject,Map.of("scopes",scopes));
    }
    public String issueToken(String subject){
        return issueToken(subject,Map.of());
    }

    public String issueToken(
            String subject,
            Map<String, Object> claims
    ) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuer("malithsumuditha")
                .issuedAt(Date.from(Instant.now()))
                .expiration(
                        Date.from(
                                Instant.now().plus(8, ChronoUnit.HOURS)
                        )
                )
                .signWith(getSigningKey())
                .compact();

    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token){
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//    private SecretKey getSigningKey() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
//    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        return getClaims(jwt).getExpiration().before(Date.from(Instant.now()));
    }
}
