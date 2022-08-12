package com.example.javalearnbook.dto.requests;

import lombok.Data;

@Data

public class BlogPostCommentsRequests {
    private String comment;
    private Long postId;
    private Long writerId;
}
