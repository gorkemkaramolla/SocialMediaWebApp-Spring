package com.example.javalearnbook.repository;

import com.example.javalearnbook.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends JpaRepository<Writer,Long> {
    Writer findByEmail(String email);



}
