package com.example.javalearnbook.service;

import com.example.javalearnbook.dto.requests.PostCreateRequest;
import com.example.javalearnbook.dto.responses.PostResponse;
import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {


    private final PostRepository postRepository;
    private final WriterService writerService;

    public PostService(PostRepository repository, WriterService writerService) {
        this.postRepository = repository;
        this.writerService = writerService;
    }
    public List<PostResponse> getWritersPosts(Optional<Long> writerId)
    {
        return writerId.map(post -> postRepository.findByWriterId(post)
                .stream().map(PostResponse::new).collect(Collectors.toList()))
                .orElseGet(() -> postRepository.findAll().stream().map(PostResponse::new).collect(Collectors.toList()));
    }


    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public PostResponse createPost( PostCreateRequest post) {
       Writer writer = writerService.getWriterById(post.getWriterId());
       if(writer !=null)
       {
            Post toSave = new Post();
            toSave.setId(post.getId());
            toSave.setContent(post.getContent());
            toSave.setTitle(post.getTitle());
            toSave.setWriter(writer);
            postRepository.save(toSave);
           return new PostResponse(toSave);
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



}














