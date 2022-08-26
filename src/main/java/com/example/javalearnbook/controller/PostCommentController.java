package com.example.javalearnbook.controller;
import com.example.javalearnbook.dto.requests.CommentsRequests;
import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.PostComment;
import com.example.javalearnbook.service.PostCommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:3000")

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
    private PostComment getCommendById(@PathVariable Long commentId)
    {
        return commentService.getCommentById(commentId);
    }


    @PutMapping("/{commentId}")
    private PostComment changeCommentById(@PathVariable Long commentId,@RequestBody CommentsRequests commentsRequests)
    {
        return commentService.changeCommentById(commentId,commentsRequests);
    }

    @DeleteMapping("/{commentId}")
    private String deleteComment(@PathVariable Long commentId)
    {
        return commentService.deleteComment(commentId);
    }








    private PostCommentController(PostCommentService commentService) {
        this.commentService = commentService;
    }
}





















