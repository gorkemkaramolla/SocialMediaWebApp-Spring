package com.example.javalearnbook.service;

import com.example.javalearnbook.model.BlogPost;
import com.example.javalearnbook.repository.BlogPostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BlogPostService {


    private BlogPostRepository blogPostRepository;

    public  List<BlogPost> getPosts()
    {

        return blogPostRepository.findAll();
    }
    public BlogPost savePost(BlogPost post)
    {
        return  blogPostRepository.save(post);
    }
    public BlogPost getSinglePost(Long id)
    {
        return blogPostRepository.findById(id).orElse(null);
    }
    public boolean updatePost(Long id, BlogPost postNew)
    {

       BlogPost postChange = blogPostRepository.findById(id).orElse(null);
       if(postChange!=null)
       {
           postChange.setTitle(postNew.getTitle());
           postChange.setContent(postNew.getContent());
           savePost(postChange);
           return true;
       }
        return false;

    }
}
