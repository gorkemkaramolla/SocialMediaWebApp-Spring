package com.example.javalearnbook.repository;

import com.example.javalearnbook.model.BlogPostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostCommentRepository extends JpaRepository<Long,BlogPostComment> {
}
