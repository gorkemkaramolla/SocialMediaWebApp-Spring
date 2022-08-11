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
    private final BlogWriterService blogWriterService;

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

    public BlogPost createPost(Long writerId, BlogPost blogPost) {
       BlogWriter blogWriter= blogWriterService.getOneWriter(writerId);
       if(blogWriter!=null)
       {
            BlogPost toSave = new BlogPost();
            toSave.setId(blogPost.getId());
            toSave.setContent(blogPost.getContent());
            toSave.setTitle(blogPost.getTitle());
            toSave.setWriter(blogWriter);
            return repository.save(toSave);
       }
        return null;
    }

    public BlogPost changePostInfo(Long postId,BlogPost changedPost) {

        BlogPost post = repository.findById(postId).orElse(null);
        if(post!=null)
        {

            post.setTitle(changedPost.getTitle());
            post.setContent(changedPost.getContent());

            return repository.save(post);

        }
        return null;
    }

    public String deletePost(Long postId) {
        Optional<BlogPost> post = repository.findById(postId);
        if(post.isPresent())
        {
            repository.deleteById(postId);
            return "Deleted";
        }
        return "Can't delete";
    }

}














