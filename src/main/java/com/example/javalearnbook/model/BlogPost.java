package com.example.javalearnbook.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private String title;
    private String author_id;
}
