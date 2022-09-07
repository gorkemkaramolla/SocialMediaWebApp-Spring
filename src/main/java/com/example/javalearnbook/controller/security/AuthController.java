package com.example.javalearnbook.controller.security;

import com.example.javalearnbook.dto.requests.WriterRequest;
import com.example.javalearnbook.dto.responses.security.AuthResponse;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.security.CookieUtil;
import com.example.javalearnbook.security.JwtTokenGenerator;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {



    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator tokenGenerator;
    private final WriterService writerService;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenGenerator tokenGenerator, WriterService writerService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.writerService = writerService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(HttpServletResponse httpServletResponse, @RequestBody WriterRequest loginRequest)
    {
        try{

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String JWT = tokenGenerator.generateJWT(authentication);
        Writer writer = writerService.getWriterByEmail(loginRequest.getEmail());


        CookieUtil.create(httpServletResponse, writer.getId().toString(), JWT, false, 1000, "localhost");

        return new ResponseEntity<>("GOOD REQUEST", HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>("BADREQUEST", HttpStatus.BAD_REQUEST);
    }

    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody WriterRequest registerRequest)
    {
        return writerService.validateCredentials(registerRequest);
    }


























}
