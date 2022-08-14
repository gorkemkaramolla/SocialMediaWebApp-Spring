package com.example.javalearnbook.dto.requests;

import lombok.Data;

@Data

public class CommentsRequests {
    private Long id ;
    private String comment;
    private Long postId;
    private Long writerId;
}
