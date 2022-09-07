package com.example.javalearnbook.service;

import com.example.javalearnbook.dto.requests.WriterRequest;
import com.example.javalearnbook.dto.responses.security.AuthResponse;
import com.example.javalearnbook.model.Writer;

import com.example.javalearnbook.repository.WriterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WriterService {

    private final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final String PASSWORD_PATTERN="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$";
    private WriterRepository writerRepository;

    private final PasswordEncoder passwordEncoder;

    public List<Writer> findAllWriters() {
        return writerRepository.findAll();
    }
    public Writer getWriterById(Long userId) {
        return writerRepository.findById(userId).orElse(null);
    }

    public Writer saveWriter(Writer writer) {
      return writerRepository.save(writer);
    }
    public Writer updateWriter(Long userId, Writer changeRequestedWriter)
    {
       Optional<Writer> blogWriter = writerRepository.findById(userId);
       if(blogWriter.isPresent())
       {
           Writer foundWriter = blogWriter.get();
           foundWriter.setName(changeRequestedWriter.getName());
           foundWriter.setLastName(changeRequestedWriter.getLastName());

            return writerRepository.save(foundWriter);
       }
       else {
           return null;
       }

    }


    public String deleteWriter(Long writerId) {
        Optional<Writer> requestedWriter = writerRepository.findById(writerId);
        if(requestedWriter.isPresent())
        {
            writerRepository.deleteById(writerId);
            return "Deleted";
        }
        else {
            return "Can't delete";
        }
    }

    public WriterService(WriterRepository writerRepository, PasswordEncoder passwordEncoder) {
        this.writerRepository = writerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Writer getWriterByEmail(String email) {
        Writer writer = writerRepository.findByEmail(email);
        return writer;
    }

    public ResponseEntity<AuthResponse> validateCredentials(WriterRequest registerRequest) {
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher emailMatcher = emailPattern.matcher(registerRequest.getEmail());

        Pattern passwordPattern =Pattern.compile(PASSWORD_PATTERN);
        Matcher passwordMatcher = passwordPattern.matcher(registerRequest.getPassword());
        AuthResponse authResponse =new AuthResponse();

        if(!emailMatcher.matches())
        {
            authResponse.setMessage("your mail is in wrong format");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        } else if (!passwordMatcher.matches()) {
            authResponse.setMessage("Password should be 8-32 length and contain at least one upper case letter ");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        else if(this.getWriterByEmail(registerRequest.getEmail()) !=null)
        {
            authResponse.setMessage("Your email is already taken");
            return new ResponseEntity<>(authResponse,HttpStatus.BAD_REQUEST);
        }
            Writer writer = new Writer();
            writer.setEmail(registerRequest.getEmail());
            writer.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            this.saveWriter(writer);
            authResponse.setMessage("Successfully Registered");
            return new ResponseEntity<>(authResponse,HttpStatus.CREATED);

    }
}























