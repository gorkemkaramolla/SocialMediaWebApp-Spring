package com.example.javalearnbook.service;

import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final WriterService writerService;

    public List<Post> getWritersPosts(Optional<Long> writerId)
    {
        if(writerId.isPresent())
        {
            return postRepository.findByWriterId(writerId.get());
        }
        return postRepository.findAll();
    }


    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
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
            return postRepository.save(toSave);
       }
        return null;
    }

    public Post changePostInfo(Long postId, Post changedPost) {

        Post post = postRepository.findById(postId).orElse(null);
        if(post!=null)
        {

            post.setTitle(changedPost.getTitle());
            post.setContent(changedPost.getContent());

            return postRepository.save(post);

        }
        return null;
    }

    public String deletePost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent())
        {
            postRepository.deleteById(postId);
            return "Deleted";
        }
        return "Can't delete";
    }


    public PostService(PostRepository repository, WriterService writerService) {
        this.postRepository = repository;
        this.writerService = writerService;
    }
}














