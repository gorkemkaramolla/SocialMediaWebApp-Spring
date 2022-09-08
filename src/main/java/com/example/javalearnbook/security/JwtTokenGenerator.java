package com.example.javalearnbook.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {
    @Value("${chatapp.application.secret}")
    private String APP_SECRET;
    @Value("${chatapp.expiration.in}")
    private Long EXPIRES_IN;
    private final AuthenticationManager authenticationManager;

    @Value("${chatapp.application.cookiename}")
    private String jwtCookieName;

    public JwtTokenGenerator(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String generateJWT(Authentication authentication)
    {



        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + EXPIRES_IN);
        String jwt =  Jwts.builder()
                .setSubject(userDetails.getId().toString())
                .setIssuedAt(now).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();


        return jwt;
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

    public String generateJwtByWriterId(Long writerId) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + EXPIRES_IN);
        String jwt =  Jwts.builder()
                .setSubject(writerId.toString())
                .setIssuedAt(now).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();


        return jwt;
    }
}
