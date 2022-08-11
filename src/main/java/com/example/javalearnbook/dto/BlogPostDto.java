package com.example.javalearnbook.dto;

import lombok.Data;
@Data
public class BlogPostDto {
    private String content;
    private String title;
    private Long writerId;
}

