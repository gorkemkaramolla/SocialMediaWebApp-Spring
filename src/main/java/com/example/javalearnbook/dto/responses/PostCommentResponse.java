package com.example.javalearnbook.dto.responses;

import com.example.javalearnbook.model.PostComment;
import lombok.Data;

@Data
public class PostCommentResponse {
    private Long id;
    private Long writerId;
    private Long postId;
    private String comment;
    private String userName;

    public PostCommentResponse(PostComment postComment) {
        this.id = postComment.getId();
        this.writerId = postComment.getWriter().getId();
        this.postId = postComment.getPost().getId();
        this.comment = postComment.getComment();
        this.userName = postComment.getWriter().getUserName();
    }
}
