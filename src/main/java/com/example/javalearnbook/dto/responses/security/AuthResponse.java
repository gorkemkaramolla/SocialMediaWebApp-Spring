package com.example.javalearnbook.dto.responses.security;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthResponse {
    private Long writerId;
    private String message;
    private String accessToken;
    private String userName;
}
