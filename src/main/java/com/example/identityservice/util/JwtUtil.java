package com.example.identityservice.util;

import com.example.identityservice.customexceptions.InvalidJwtToken;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    @Value("${jwt.expireInMsAccessToken}")
    private int expireInMsAccessToken;

   // private final static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    public String generateAccessToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuer("hajiribox.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireInMsAccessToken))
                .signWith(SignatureAlgorithm.HS256,jwtSecretKey)
                .compact();
    }

    public String generateRefreshToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuer("hajribox.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + expireInMsRefreshToken))
                .signWith(SignatureAlgorithm.HS256,jwtSecretKey)
                .compact();
    }

    public boolean validateAccessToken(String token) throws InvalidJwtToken {
        if (getUsername(token) != null && isExpired(token)) {
            return true;
        }
        return false;
    }

//    public boolean validateRefreshTokenSign(String token) {
//        if (getUsername(token) != null) {
//            return true;
//        }
//        return false;
//    }

    public String getUsername(String token) throws InvalidJwtToken {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) throws InvalidJwtToken {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) throws InvalidJwtToken{
        try {
            return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
        }
        catch(ExpiredJwtException exception)
        {
            throw new InvalidJwtToken("Token expired");
        }
        catch(SignatureException exception)
        {
            throw new InvalidJwtToken("Invalid token");
        }
        catch (Exception e) {
            throw new InvalidJwtToken("Invalid JWT token. Cannot Parse the token");
        }
    }
}
