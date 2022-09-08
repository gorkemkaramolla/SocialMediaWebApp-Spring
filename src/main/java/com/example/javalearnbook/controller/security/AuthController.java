package com.example.javalearnbook.controller.security;

import com.example.javalearnbook.dto.requests.RefreshRequest;
import com.example.javalearnbook.dto.requests.WriterRequest;
import com.example.javalearnbook.dto.responses.security.AuthResponse;
import com.example.javalearnbook.model.RefreshToken;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.security.CookieUtil;
import com.example.javalearnbook.security.JwtTokenGenerator;
import com.example.javalearnbook.service.RefreshTokenService;
import com.example.javalearnbook.service.WriterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator tokenGenerator;
    private final RefreshTokenService refreshTokenService;
    private final WriterService writerService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenGenerator tokenGenerator, WriterService writerService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.writerService = writerService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(HttpServletResponse httpServletResponse, @RequestBody WriterRequest loginRequest)
    {
            Writer writer = writerService.getWriterByEmail(loginRequest.getEmail());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String JWT = tokenGenerator.generateJWT(authentication);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest);
        CookieUtil.create(httpServletResponse, "session", refreshToken.getRefreshToken(), false, 60, "localhost",writer.getId().toString());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJWT(JWT);
        authResponse.setRefreshToken(refreshToken.getRefreshToken());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);


    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody WriterRequest registerRequest)
    {
        return writerService.validateCredentials(registerRequest);
    }
    @PostMapping("/refresh")
    public  ResponseEntity<Object> refreshToken(HttpServletResponse httpServletResponse,@RequestBody RefreshRequest refreshRequest)
    {
        String JWT =refreshTokenService.refreshTokenRequestHandler(refreshRequest);
        CookieUtil.create(httpServletResponse, "session", JWT, false, 60, "localhost",refreshRequest.getWriterId().toString());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJWT(JWT);
        try {
            return new ResponseEntity<>(authResponse,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e,HttpStatus.UNAUTHORIZED);
        }
    }


























}
