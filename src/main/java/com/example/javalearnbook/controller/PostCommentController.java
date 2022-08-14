package com.example.javalearnbook.controller;
import com.example.javalearnbook.dto.requests.CommentsRequests;
import com.example.javalearnbook.model.PostComment;
import com.example.javalearnbook.service.PostCommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class PostCommentController {
    private final PostCommentService commentService;
    @GetMapping()
    private List<PostComment> getComments(@RequestParam Optional<Long> postId, @RequestParam Optional<Long> writerId)
    {
        return commentService.getComments(writerId,postId);
    }
    @PostMapping
    private PostComment createComment(@RequestBody CommentsRequests blogPostComment)
    {
        return commentService.createComment(blogPostComment);
    }
    @GetMapping("/{commentId}")
    public PostComment getCommendById(@PathVariable Long commentId)
    {
        return commentService.getCommentById(commentId);
    }
}





















