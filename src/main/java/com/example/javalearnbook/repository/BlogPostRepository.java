package com.example.javalearnbook.repository;

import com.example.javalearnbook.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost,Long> {
    List<BlogPost> findByWriterId(Long userId);
}
