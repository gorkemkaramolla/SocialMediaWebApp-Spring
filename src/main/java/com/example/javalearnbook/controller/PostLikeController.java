package com.example.javalearnbook.controller;

import com.example.javalearnbook.dto.requests.PostLikePostRequest;
import com.example.javalearnbook.dto.responses.PostLikeResponse;
import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.PostLike;
import com.example.javalearnbook.service.PostLikeService;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postlikes")
@CrossOrigin(origins = "http://localhost:3000")

public class PostLikeController {
    private final PostLikeService postLikeService;
    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @GetMapping()
    public List<PostLikeResponse> getPostLikes(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> writerId)
    {
        return postLikeService.getPostLikes(postId);
    }
    @PostMapping
    public PostLike savePostLikes(@RequestBody PostLikePostRequest postLikePostRequest)
    {
        return postLikeService.savePost(postLikePostRequest);
    }
    @DeleteMapping("/{likeId}")
    public String deleteLike(@PathVariable Long likeId)
    {
        return postLikeService.deleteLike(likeId);
    }
}
