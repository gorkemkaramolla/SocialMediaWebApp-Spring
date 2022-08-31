package com.example.javalearnbook.security;

import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.repository.WriterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    private final WriterRepository writerRepository;

    public JwtUserDetailsServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Writer writer = writerRepository.findByEmail(email);
        return JwtUserDetails.create(writer);

    }
    public UserDetails loadWriterById(Long id) {

        Writer writer = writerRepository.findById(id).get();
        return JwtUserDetails.create(writer);

    }

}
