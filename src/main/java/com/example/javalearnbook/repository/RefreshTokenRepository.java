package com.example.javalearnbook.repository;

import com.example.javalearnbook.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findByWriterId(Long writerId);
}
