package finance_management.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;

    public JwtService(@Value("${app.jwt.secret}") String secretKey){
        this.secretKey = getInSecretKey(secretKey);
    }

    public SecretKey getInSecretKey(String key){
        byte[] keys = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keys);
    }


    public String generateToken(String userEmail){
        return Jwts.builder()
                .subject(userEmail)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 24))
                .signWith(secretKey)
                .compact();
    }

    public String extractUserEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        Claims claims = extractAllClaims(token);
        String userName = claims.getSubject();
        boolean isExpired = claims.getExpiration().before(new Date());
        return userName.equals(userDetails.getUsername()) && !isExpired;
    }

}
