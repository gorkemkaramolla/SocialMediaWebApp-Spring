package com.example.javalearnbook.service;

import com.example.javalearnbook.dto.BlogWriterDto;
import com.example.javalearnbook.model.BlogPost;
import com.example.javalearnbook.model.BlogWriter;
import com.example.javalearnbook.repository.BlogPostRepository;
import com.example.javalearnbook.repository.BlogWriterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BlogWriterService {


    private BlogWriterRepository blogWriterRepository;


    public List<BlogWriter> findAllWriters() {
        return blogWriterRepository.findAll();
    }

    public BlogWriter saveWriter(BlogWriter blogWriter) {
      return blogWriterRepository.save(blogWriter);
    }
    public BlogWriter updateWriter(Long userId, BlogWriter changeRequestedWriter)
    {
        BlogWriter blogWriter = blogWriterRepository.findById(userId).orElse(null);
        blogWriter.setName(changeRequestedWriter.getName());
        blogWriter.setLastName(changeRequestedWriter.getLastName());
        return blogWriter;


    }
}























