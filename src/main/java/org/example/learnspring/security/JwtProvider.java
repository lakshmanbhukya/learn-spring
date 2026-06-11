package org.example.learnspring.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final String SECRET_KEY = "ba17d6c270cdf379ee5f4148e270db98b92e05cc980ee7c1804dc3bed1a2a481";
    private final SecretKey KEY= Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final long exp_date=86400000;

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+exp_date))
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
