package com.example.javalearnbook.service;

import com.example.javalearnbook.model.Writer;

import com.example.javalearnbook.repository.WriterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WriterService {


    private WriterRepository writerRepository;


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

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }
}























