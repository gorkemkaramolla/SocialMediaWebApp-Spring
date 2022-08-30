package com.example.javalearnbook.dto.responses;

import com.example.javalearnbook.model.PostLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PostLikeResponse {
    private Long id;
    private Long userId;
    private Long postId;

    public PostLikeResponse(PostLike postLike) {
        this.id = postLike.getId();
        this.userId=postLike.getWriter().getId();
        this.postId = postLike.getPost().getId();
    }
}
