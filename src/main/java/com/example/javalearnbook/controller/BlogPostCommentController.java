package com.example.javalearnbook.controller;

import com.example.javalearnbook.dto.requests.BlogPostCommentsRequests;
import com.example.javalearnbook.model.BlogPostComment;
import com.example.javalearnbook.service.BlogPostCommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class BlogPostCommentController {
    private final BlogPostCommentService commentService;
    @GetMapping()
    private List<BlogPostComment> getPostsComments(@RequestParam Optional<Long> postId)
    {
        return commentService.getPostsComments(postId);
    }
    @PostMapping
    private BlogPostComment createComment( @RequestBody BlogPostCommentsRequests blogPostComment)
    {
        return commentService.createComment(blogPostComment);
    }
}





















