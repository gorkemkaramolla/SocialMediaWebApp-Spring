package com.example.javalearnbook.security;

import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public JwtAuthenticationFilter(JwtTokenGenerator tokenGenerator, JwtUserDetailsServiceImpl userDetailsService) {
        this.tokenGenerator = tokenGenerator;
        this.userDetailsService = userDetailsService;

    }

    private JwtTokenGenerator tokenGenerator;
    private  JwtUserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String JWT = extractJwtFromRequest(request);
            if(JWT!=null && tokenGenerator.validateToken(JWT))
            {
                Long writerId =  tokenGenerator.getWriterIdFromJWT(JWT);
                UserDetails writer = userDetailsService.loadWriterById(writerId);
                if(writer != null)
                {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(writer,null,writer.getAuthorities());

                    authenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        filterChain.doFilter(request,response);

    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearer =request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
        {
            return  bearer.substring("Bearer".length()+1);
        }
        return null;
    }



}
