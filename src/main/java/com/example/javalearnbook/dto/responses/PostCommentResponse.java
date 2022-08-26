package com.example.javalearnbook.dto.responses;

import com.example.javalearnbook.model.PostComment;
import lombok.Data;

@Data
public class PostCommentResponse {
    private Long id;
    private Long writerId;
    private Long postId;
    private String comment;

    public PostCommentResponse(PostComment postComment) {
    }
}
