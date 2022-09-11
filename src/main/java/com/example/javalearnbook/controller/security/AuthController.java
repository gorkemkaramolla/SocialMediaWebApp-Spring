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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")

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
        CookieUtil.create(httpServletResponse, "session", writer.getId()+"/"+refreshToken.getRefreshToken(), false, 500, "localhost");
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(JWT);
        authResponse.setWriterId(writer.getId());

        authResponse.setMessage("Successfully Logged in");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);


    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody WriterRequest registerRequest)
    {
        return writerService.validateCredentials(registerRequest);
    }
    @PostMapping("/refresh")
    public  ResponseEntity<Object> refreshToken(HttpServletRequest httpServletRequest)
    {
        List<Cookie> cookies = Arrays.stream(httpServletRequest.getCookies()).toList();


        Cookie session = cookies.stream().filter(cookie -> cookie.getName().equals("session")).findAny().orElse(null);
        if(session!=null)
        {
            RefreshRequest refreshRequest = new RefreshRequest();
            refreshRequest.setRefreshToken(session.getValue().split("/")[1]);
            Long writerID = Long.parseLong(session.getValue().split("/")[0]);
            refreshRequest.setWriterId(writerID);
            String JWT =refreshTokenService.refreshTokenRequestHandler(refreshRequest);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Access Token Refreshed JWT");
            authResponse.setAccessToken(JWT);
            authResponse.setWriterId(writerID);

                return new ResponseEntity<>(authResponse,HttpStatus.OK);



        }
        return new ResponseEntity<>("ERROR",HttpStatus.UNAUTHORIZED);



    }


























}
