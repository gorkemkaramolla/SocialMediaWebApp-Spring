package com.example.javalearnbook.security;

import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.repository.WriterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {
    private final WriterRepository writerRepository;

    public JwtUserDetailServiceImpl(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {

        Writer writer = writerRepository.findByEmail(eMail);
        return JwtUserDetails.create(writer);

    }
    public UserDetails loadUserByUserId(Long id) {

        Writer writer = writerRepository.finByUserId(id);
        return JwtUserDetails.create(writer);

    }

}
