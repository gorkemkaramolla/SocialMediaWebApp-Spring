package com.example.javalearnbook.repository;

import com.example.javalearnbook.dto.requests.PostLikePostRequest;
import com.example.javalearnbook.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    List<PostLike> findByPostId(Long postId);
}
