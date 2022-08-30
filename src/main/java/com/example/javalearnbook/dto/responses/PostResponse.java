package com.example.javalearnbook.dto.responses;

import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.PostLike;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private Long userId;
    private String name;
    private String lastName;
    private String title;
    private String content;
    private Set<PostLikeResponse> postLikes;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.userId = entity.getWriter().getId();
        this.name = entity.getWriter().getName();
        this.lastName = entity.getWriter().getLastName();
        this.title = entity.getTitle();
        this.content = entity.getContent();

        Set<PostLike> likes = entity.getLikes();
        this.postLikes=likes.stream().map(PostLikeResponse::new).collect(Collectors.toSet());


    }
}
