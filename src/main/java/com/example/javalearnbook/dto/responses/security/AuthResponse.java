package com.example.javalearnbook.dto.responses.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;

@Getter
@Setter
public class AuthResponse {
    private Long id;
    private String message;
    private Cookie httpOnly;
}
