package com.example.javalearnbook.repository;

import com.example.javalearnbook.model.BlogPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
@Repository
public interface BlogPostCommentRepository extends JpaRepository<BlogPostComment,Long> {

     List<BlogPostComment> findByPostId(Long postId);
}
