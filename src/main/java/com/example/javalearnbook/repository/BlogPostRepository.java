package com.example.javalearnbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPostRepository,Long> {
}
