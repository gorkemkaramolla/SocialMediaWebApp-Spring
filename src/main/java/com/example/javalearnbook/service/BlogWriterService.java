package com.example.javalearnbook.service;

import com.example.javalearnbook.model.BlogWriter;

import com.example.javalearnbook.repository.BlogWriterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogWriterService {


    private BlogWriterRepository blogWriterRepository;


    public List<BlogWriter> findAllWriters() {
        return blogWriterRepository.findAll();
    }
    public BlogWriter getOneWriter(Long userId) {
        return blogWriterRepository.findById(userId).orElse(null);
    }

    public BlogWriter saveWriter(BlogWriter blogWriter) {
      return blogWriterRepository.save(blogWriter);
    }
    public BlogWriter updateWriter(Long userId, BlogWriter changeRequestedWriter)
    {
       Optional<BlogWriter> blogWriter = blogWriterRepository.findById(userId);
       if(blogWriter.isPresent())
       {
           BlogWriter foundWriter = blogWriter.get();
           foundWriter.setName(changeRequestedWriter.getName());
           foundWriter.setLastName(changeRequestedWriter.getLastName());

            return blogWriterRepository.save(foundWriter);
       }
       else {
           return null;
       }

    }


    public String deleteWriter(Long writerId) {
        Optional<BlogWriter> requestedWriter = blogWriterRepository.findById(writerId);
        if(requestedWriter.isPresent())
        {
            blogWriterRepository.deleteById(writerId);
            return "Deleted";
        }
        else {
            return "Can't delete";
        }
    }
}























