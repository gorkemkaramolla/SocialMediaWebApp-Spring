package com.example.javalearnbook.controller.security;

import com.example.javalearnbook.dto.requests.WriterRequest;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.security.JwtTokenGenerator;
import com.example.javalearnbook.service.WriterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator tokenGenerator;
    private final WriterService writerService;
    private final PasswordEncoder passwordEncoder;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenGenerator tokenGenerator, WriterService writerService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.writerService = writerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody WriterRequest loginRequest)
    {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String JWT = tokenGenerator.generateJWT(authentication);

        return "Bearer "+JWT;

    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Writer registerRequest)
    {
        if(writerService.getWriterByEmail(registerRequest.getEmail()))
        {
            return new ResponseEntity<>("Your email is already taken",HttpStatus.BAD_REQUEST);
        }
        Writer writer = new Writer();
        writer.setEmail(registerRequest.getEmail());
        writer.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        writerService.saveWriter(writer);
        return new ResponseEntity<>("Successfully Registered",HttpStatus.CREATED);
    }

}
