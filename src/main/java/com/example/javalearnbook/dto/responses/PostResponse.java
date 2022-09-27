package com.example.javalearnbook.dto.responses;

import com.example.javalearnbook.model.Post;
import com.example.javalearnbook.model.PostLike;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private Long userId;
    private String userName;
    private String title;
    private String content;
    private String imagePath;
    private Set<PostLikeResponse> postLikes;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.userId = entity.getWriter().getId();
        this.userName = entity.getWriter().getUserName();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.imagePath=entity.getWriter().getImgPath();

        Set<PostLike> likes = entity.getLikes();
        this.postLikes=likes.stream().map(PostLikeResponse::new).collect(Collectors.toSet());


    }
}
