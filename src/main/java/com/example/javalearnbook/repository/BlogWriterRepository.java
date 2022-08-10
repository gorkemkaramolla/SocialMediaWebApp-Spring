package com.example.javalearnbook.repository;

import com.example.javalearnbook.model.BlogWriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogWriterRepository extends JpaRepository<BlogWriter,Long> {
}
