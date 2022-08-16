package com.example.javalearnbook.dto.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter

public class CommentsRequests {
    private Long id ;
    private String comment;
    private Long postId;
    private Long writerId;
}
