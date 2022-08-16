package com.example.javalearnbook.repository;

import com.example.javalearnbook.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<PostComment,Long> {

     List<PostComment> findByPostId(Long postId);

    List <PostComment> findByWriterId(Long writerId);

    List<PostComment> findByWriterIdAndPostId(Long writerId, Long postId);
}
