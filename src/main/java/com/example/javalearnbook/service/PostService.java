package com.example.javalearnbook.service;

import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository repository;
    private final WriterService writerService;

    public List<Post> getWritersPosts(Optional<Long> writerId)
    {
        if(writerId.isPresent())
        {
            return repository.findByWriterId(writerId.get());
        }
        return repository.findAll();
    }


    public Post getPostById(Long postId) {
        return repository.findById(postId).orElse(null);
    }

    public Post createPost(Long writerId, Post post) {
       Writer writer = writerService.getWriterById(writerId);
       if(writer !=null)
       {
            Post toSave = new Post();
            toSave.setId(post.getId());
            toSave.setContent(post.getContent());
            toSave.setTitle(post.getTitle());
            toSave.setWriter(writer);
            return repository.save(toSave);
       }
        return null;
    }

    public Post changePostInfo(Long postId, Post changedPost) {

        Post post = repository.findById(postId).orElse(null);
        if(post!=null)
        {

            post.setTitle(changedPost.getTitle());
            post.setContent(changedPost.getContent());

            return repository.save(post);

        }
        return null;
    }

    public String deletePost(Long postId) {
        Optional<Post> post = repository.findById(postId);
        if(post.isPresent())
        {
            repository.deleteById(postId);
            return "Deleted";
        }
        return "Can't delete";
    }


    public PostService(PostRepository repository, WriterService writerService) {
        this.repository = repository;
        this.writerService = writerService;
    }
}














