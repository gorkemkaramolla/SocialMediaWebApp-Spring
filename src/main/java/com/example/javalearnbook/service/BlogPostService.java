package com.example.javalearnbook.service;

import com.example.javalearnbook.model.BlogPost;
import com.example.javalearnbook.model.BlogWriter;
import com.example.javalearnbook.repository.BlogPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogPostService {

    private final BlogPostRepository repository;

    public List<BlogPost> getWritersPosts(Optional<Long> writerId)
    {
        if(writerId.isPresent())
        {
            return repository.findByWriterId(writerId.get());
        }
        return repository.findAll();
    }


    public BlogPost getPostById(Long postId) {
        return repository.findById(postId).orElse(null);
    }

    public BlogPost createPost(BlogPost post) {
        return repository.save(post);
    }
}














