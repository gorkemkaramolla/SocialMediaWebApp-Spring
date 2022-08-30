package com.example.javalearnbook.service;

import com.example.javalearnbook.dto.requests.PostLikePostRequest;
import com.example.javalearnbook.dto.responses.PostLikeResponse;
import com.example.javalearnbook.dto.responses.PostResponse;
import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.PostLike;
import com.example.javalearnbook.model.Writer;
import com.example.javalearnbook.repository.PostLikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final WriterService writerService;
    private final PostService postService;

    public PostLikeService(PostLikeRepository postLikeRepository, WriterService writerService, PostService postService) {
        this.postLikeRepository = postLikeRepository;
        this.writerService = writerService;
        this.postService = postService;
    }

    public List<PostLikeResponse> getPostLikes(Optional<Long> postId) {
        if(postId.isPresent()) {

            List<PostLike> postLikes = postId.map(postLikeRepository::findByPostId).orElse(null);
              return postLikes.stream().map(PostLikeResponse::new).collect(Collectors.toList());
        }
        return null;
    }

    public PostLike savePost(PostLikePostRequest postLikePostRequest) {

        Writer writer = writerService.getWriterById(postLikePostRequest.getWriterId());
        Post post = postService.getPostById(postLikePostRequest.getPostId());
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setWriter(writer);
        return postLikeRepository.save(postLike);
    }

    public String deleteLike(Long id) {
        Optional<PostLike> postLike = postLikeRepository.findById(id);
        if(postLike.isPresent())
        {
            postLikeRepository.deleteById(id);
            return "Like Deleted";
        }
        return  "Like is not exist";
    }
}
