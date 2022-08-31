package com.example.javalearnbook.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {
    @Value("${chatapp.application.secret}")
    private String APP_SECRET;
    @Value("${chatapp.expiration.in}")
    private Long EXPIRES_IN;

    public String generateJWT(Authentication auth)
    {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + EXPIRES_IN);
        return Jwts.builder()
                .setSubject(userDetails.getId().toString())
                .setIssuedAt(now).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();
    }
    public Long getWriterIdFromJWT(String token)
    {

        return Long.parseLong(parseGetBody(token).getSubject());
    }
    public boolean validateToken(String token)
    {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        }
        catch (SignatureException | UnsupportedJwtException | IllegalArgumentException | ExpiredJwtException |
               MalformedJwtException exception) {
            return false;
        }
    }
    private boolean isTokenExpired(String token) {
        Claims claims = parseGetBody(token);
        Date dateCreated = claims.getIssuedAt();
        return !dateCreated.before(claims.getExpiration());

    }
    private Claims parseGetBody(String token)
    {
        return  Jwts.parser().setSigningKey(APP_SECRET)
                .parseClaimsJws(token).getBody();
    }

}
