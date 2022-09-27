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

    public PostLike savePostLikes(PostLikePostRequest postLikePostRequest) {

        Writer writer = writerService.getWriterById(Long.parseLong(postLikePostRequest.getWriterId()));
        Post post = postService.getPostById(postLikePostRequest.getPostId());

        PostLike postLike = postLikeRepository.findByWriterIdAndPostId(writer.getId(),post.getId());
        if(postLike== null)
        {
            PostLike insertedPostLike = new PostLike();
            insertedPostLike.setPost(post);
            insertedPostLike.setWriter(writer);
            return postLikeRepository.save(insertedPostLike);

        }
        return null;
    }

    public String deleteLike(PostLikePostRequest deleteRequest) {
        PostLike postLike = postLikeRepository.findByWriterIdAndPostId(Long.parseLong(deleteRequest.getWriterId()),deleteRequest.getPostId());
        if(postLike!=null)
        {
            postLikeRepository.deleteById(postLike.getId());
            return "Like Deleted";
        }
        return  "Like is not exist";
    }
}
