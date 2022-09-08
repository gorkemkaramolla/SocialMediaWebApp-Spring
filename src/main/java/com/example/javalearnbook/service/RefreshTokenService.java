package com.example.javalearnbook.service;

import com.example.javalearnbook.dto.requests.RefreshRequest;
import com.example.javalearnbook.dto.requests.WriterRequest;
import com.example.javalearnbook.model.RefreshToken;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.repository.RefreshTokenRepository;
import com.example.javalearnbook.security.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${chatapplication.refreshtoken.expiretime}")
    private Long expiretime;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;

    private final WriterService writerService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AuthenticationManager authenticationManager, JwtTokenGenerator tokenGenerator, AuthenticationManager authenticationManager1, WriterService writerService) {
        this.refreshTokenRepository=refreshTokenRepository;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager1;
        this.writerService = writerService;
    }


    public RefreshToken createRefreshToken(WriterRequest writerRequest)
    {
        Writer writer = writerService.getWriterByEmail(writerRequest.getEmail());
        RefreshToken dbRefreshToken =refreshTokenRepository.findByWriterId(writer.getId());
       if (dbRefreshToken ==null)
       {
           RefreshToken refreshToken = new RefreshToken();
           refreshToken.setWriter(writer);
           refreshToken.setRefreshToken(UUID.randomUUID().toString());
           refreshToken.setExpirationDate(Date.from(Instant.now().plusSeconds(expiretime)));
           return refreshTokenRepository.save(refreshToken);
       }
       else
       {
           return dbRefreshToken;
       }
    }

    public String refreshTokenRequestHandler(RefreshRequest refreshRequest) {
       RefreshToken dbRefreshToken =  refreshTokenRepository.findByWriterId(refreshRequest.getWriterId());
       String requestRefreshToken = refreshRequest.getRefreshToken();
       Writer writer = writerService.getWriterById(refreshRequest.getWriterId());
        if(dbRefreshToken.getRefreshToken().equals(requestRefreshToken) )
        {
            if(isExpired(dbRefreshToken))
            {
                if(writer!=null)
                {
                    return tokenGenerator.generateJwtByWriterId(writer.getId());

                }
            }
            else {
                return null;
            }
        }
        return null;
    }
    public boolean isExpired(RefreshToken refreshToken)
    {
         if(new Date().before(refreshToken.getExpirationDate())){
             return true;
         }
         else
             return false;

    }
}
