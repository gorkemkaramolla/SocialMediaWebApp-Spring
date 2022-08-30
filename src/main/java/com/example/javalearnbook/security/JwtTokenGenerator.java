package com.example.javalearnbook.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator {
    @Value("${chatapp.application.secret}")
    private String APP_SECRET;
    @Value("${chatapp.application.expiration}")
    private Long EXPIRES_IN;

}
